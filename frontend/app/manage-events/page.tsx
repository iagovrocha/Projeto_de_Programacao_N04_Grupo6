"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"
import EditEventModal from "@/components/edit-event-modal"
import CreateNotificationModal from "@/components/create-notification-modal"
import { Edit, Trash2, Bell } from "lucide-react"

interface Event {
  id: string
  nome: string
  local: string
  data: string
  idUser: number
}

interface User {
  id: string
  nome: string
  email: string
  role: string
}

export default function ManageEventsPage() {
  const [user, setUser] = useState<User | null>(null)
  const [allEvents, setAllEvents] = useState<Event[]>([])
  const [paginatedEvents, setPaginatedEvents] = useState<Event[]>([])
  const [loading, setLoading] = useState(true)
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [isEditModalOpen, setIsEditModalOpen] = useState(false)
  const [isNotificationModalOpen, setIsNotificationModalOpen] = useState(false)
  const [selectedEvent, setSelectedEvent] = useState<Event | null>(null)
  const itemsPerPage = 5
  const router = useRouter()

  useEffect(() => {
    const userStr = localStorage.getItem("user")

    if (!userStr) {
      router.push("/login")
      return
    }

    const parsedUser = JSON.parse(userStr)
    if (parsedUser.role !== "ORGANIZADOR") {
      router.push("/dashboard")
      return
    }

    setUser(parsedUser)
    fetchEvents(parsedUser.id)
  }, [router])

  useEffect(() => {
    applyPagination()
  }, [allEvents, currentPage])

  const fetchEvents = async (userId: string) => {
    setLoading(true)
    try {
      // Buscar todos os eventos do organizador
      const response = await fetch(`http://localhost:8080/eventos/createByOrg/${userId}?page=0&size=1000&sort=id,desc`)
      if (response.ok) {
        const data = await response.json()
        const eventsList = data.content || []
        setAllEvents(eventsList)
      }
    } catch (err) {
      console.error("Failed to fetch events:", err)
    } finally {
      setLoading(false)
    }
  }

  const applyPagination = () => {
    // Calcular paginação
    const totalPgs = Math.ceil(allEvents.length / itemsPerPage)
    setTotalPages(totalPgs)

    // Paginar resultados
    const startIndex = currentPage * itemsPerPage
    const endIndex = startIndex + itemsPerPage
    setPaginatedEvents(allEvents.slice(startIndex, endIndex))
  }

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      setCurrentPage(currentPage + 1)
    }
  }

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      setCurrentPage(currentPage - 1)
    }
  }

  const handlePageClick = (page: number) => {
    setCurrentPage(page)
  }

  const handleEditClick = (event: Event) => {
    setSelectedEvent(event)
    setIsEditModalOpen(true)
  }

  const handleNotificationClick = (event: Event) => {
    setSelectedEvent(event)
    setIsNotificationModalOpen(true)
  }

  const handleCloseModal = () => {
    setIsEditModalOpen(false)
    setSelectedEvent(null)
  }

  const handleCloseNotificationModal = () => {
    setIsNotificationModalOpen(false)
    setSelectedEvent(null)
  }

  const handleEventUpdated = () => {
    if (user) {
      fetchEvents(user.id)
    }
  }

  const handleNotificationCreated = () => {
    // Refresh pode ser necessário ou apenas fechar o modal
  }

  const handleDelete = async (eventId: string) => {
    if (!window.confirm("Tem certeza que deseja deletar este evento?")) return

    try {
      const response = await fetch(`http://localhost:8080/eventos/${eventId}`, {
        method: "DELETE",
      })
      if (response.ok) {
        alert("Evento deletado com sucesso")
        const userStr = localStorage.getItem("user")
        if (userStr) {
          const parsedUser = JSON.parse(userStr)
          fetchEvents(parsedUser.id)
        }
      }
    } catch (err) {
      console.error("Failed to delete event:", err)
      alert("Falha ao deletar evento")
    }
  }

  if (loading || !user) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
      </div>
    )
  }

  return (
    <div className="flex h-screen">
      <Sidebar userType={user.role} />
      <div className="flex-1 ml-64 flex flex-col">
        <Navbar userName={user.nome} />
        <main className="flex-1 overflow-auto p-6 bg-background">
          <div className="flex justify-between items-center mb-6">
            <div>
              <h2 className="text-2xl font-bold text-foreground">Gerenciar Eventos</h2>
              <p className="text-muted-foreground mt-2">Eventos que você organizou</p>
            </div>
            <a
              href="/create-event"
              className="px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90 font-medium"
            >
              Criar Novo Evento
            </a>
          </div>

          {paginatedEvents.length > 0 ? (
            <>
              <div className="space-y-4">
                {paginatedEvents.map((event) => (
                  <div
                    key={event.id}
                    className="bg-card rounded-lg p-6 border border-border flex items-center justify-between hover:shadow-lg transition"
                  >
                    <div className="flex-1">
                      <h3 className="text-lg font-bold text-foreground">{event.nome}</h3>
                      <div className="flex gap-4 mt-3 text-sm text-muted-foreground">
                        <span>📅 {new Date(event.data).toLocaleDateString('pt-BR', { 
                          day: '2-digit', 
                          month: '2-digit', 
                          year: 'numeric',
                          hour: '2-digit',
                          minute: '2-digit'
                        })}</span>
                        <span>📍 {event.local}</span>
                      </div>
                    </div>

                    <div className="flex gap-2 ml-4">
                      <button 
                        onClick={() => handleNotificationClick(event)}
                        className="p-2 hover:bg-primary/10 rounded-lg text-primary"
                        title="Criar notificação para inscritos"
                      >
                        <Bell size={20} />
                      </button>
                      <button 
                        onClick={() => handleEditClick(event)}
                        className="p-2 hover:bg-secondary rounded-lg text-foreground"
                        title="Editar evento"
                      >
                        <Edit size={20} />
                      </button>
                      <button
                        onClick={() => handleDelete(event.id)}
                        className="p-2 hover:bg-destructive/10 rounded-lg text-destructive"
                        title="Deletar evento"
                      >
                        <Trash2 size={20} />
                      </button>
                    </div>
                  </div>
                ))}
              </div>

              {totalPages > 1 && (
                <div className="mt-6 flex items-center justify-between border-t border-border pt-4">
                  <div className="text-sm text-muted-foreground">
                    Mostrando {currentPage * itemsPerPage + 1} - {Math.min((currentPage + 1) * itemsPerPage, allEvents.length)} de {allEvents.length} eventos
                  </div>
                  <div className="flex items-center gap-2">
                    <button
                      onClick={handlePreviousPage}
                      disabled={currentPage === 0}
                      className="px-3 py-2 border border-border rounded-lg bg-background text-foreground hover:bg-secondary disabled:opacity-50 disabled:cursor-not-allowed transition"
                    >
                      Anterior
                    </button>
                    
                    <div className="flex gap-1">
                      {Array.from({ length: totalPages }, (_, i) => i).map((page) => (
                        <button
                          key={page}
                          onClick={() => handlePageClick(page)}
                          className={`px-3 py-2 rounded-lg transition ${
                            currentPage === page
                              ? "bg-primary text-primary-foreground font-medium"
                              : "bg-background text-foreground hover:bg-secondary border border-border"
                          }`}
                        >
                          {page + 1}
                        </button>
                      ))}
                    </div>

                    <button
                      onClick={handleNextPage}
                      disabled={currentPage >= totalPages - 1}
                      className="px-3 py-2 border border-border rounded-lg bg-background text-foreground hover:bg-secondary disabled:opacity-50 disabled:cursor-not-allowed transition"
                    >
                      Próxima
                    </button>
                  </div>
                </div>
              )}
            </>
          ) : (
            <div className="bg-card rounded-lg p-12 border border-border text-center">
              <p className="text-muted-foreground mb-4">Você ainda não criou nenhum evento</p>
              <a
                href="/create-event"
                className="inline-block px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90"
              >
                Criar Seu Primeiro Evento
              </a>
            </div>
          )}
        </main>
      </div>

      {selectedEvent && (
        <>
          <EditEventModal
            event={selectedEvent}
            isOpen={isEditModalOpen}
            onClose={handleCloseModal}
            onEventUpdated={handleEventUpdated}
          />
          <CreateNotificationModal
            event={selectedEvent}
            isOpen={isNotificationModalOpen}
            onClose={handleCloseNotificationModal}
            onNotificationCreated={handleNotificationCreated}
          />
        </>
      )}
    </div>
  )
}
