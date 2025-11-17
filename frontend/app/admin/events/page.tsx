"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"
import { Calendar, MapPin, Edit, Trash2, User, ChevronLeft, ChevronRight } from "lucide-react"
import EditEventModal from "@/components/edit-event-modal"

interface Event {
  id: number
  nome: string
  local: string
  data: string
  idUser: number
  preco: number
}

interface User {
  id: string
  nome: string
  email: string
  role: string
}

export default function AdminEventsPage() {
  const [user, setUser] = useState<User | null>(null)
  const [allEvents, setAllEvents] = useState<Event[]>([])
  const [loading, setLoading] = useState(true)
  const [editingEvent, setEditingEvent] = useState<Event | null>(null)
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const itemsPerPage = 10
  const router = useRouter()

  useEffect(() => {
    const userStr = localStorage.getItem("user")

    if (!userStr) {
      router.push("/login")
      return
    }

    const parsedUser = JSON.parse(userStr)
    
    if (parsedUser.role !== "ADMINISTRADOR") {
      router.push("/dashboard")
      return
    }

    setUser(parsedUser)
    fetchAllEvents()
  }, [router])

  const fetchAllEvents = async () => {
    try {
      setLoading(true)
      const response = await fetch("http://localhost:8080/eventos")
      if (response.ok) {
        const data = await response.json()
        setAllEvents(data)
        setTotalPages(Math.ceil(data.length / itemsPerPage))
      }
    } catch (err) {
      console.error("Failed to fetch events:", err)
    } finally {
      setLoading(false)
    }
  }

  const handleEditClick = (event: Event) => {
    setEditingEvent(event)
  }

  const handleEventUpdated = () => {
    fetchAllEvents()
    setEditingEvent(null)
  }

  const handleDelete = async (eventId: number) => {
    if (confirm("Tem certeza que deseja deletar este evento?")) {
      try {
        const response = await fetch(`http://localhost:8080/eventos/${eventId}`, {
          method: "DELETE",
        })
        if (response.ok) {
          alert("Evento deletado com sucesso")
          fetchAllEvents()
        } else {
          alert("Falha ao deletar evento")
        }
      } catch (err) {
        console.error("Failed to delete event:", err)
        alert("Erro ao deletar evento")
      }
    }
  }

  const paginatedEvents = allEvents.slice(
    currentPage * itemsPerPage,
    (currentPage + 1) * itemsPerPage
  )

  const handlePageChange = (newPage: number) => {
    if (newPage >= 0 && newPage < totalPages) {
      setCurrentPage(newPage)
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
          <div className="mb-6">
            <h2 className="text-2xl font-bold text-foreground">Gerenciar Todos os Eventos</h2>
            <p className="text-muted-foreground mt-2">
              Total de eventos: {allEvents.length}
            </p>
          </div>

          {paginatedEvents.length > 0 ? (
            <>
              <div className="bg-card rounded-lg border border-border overflow-hidden">
                <table className="w-full">
                  <thead className="bg-muted">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        ID
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Evento
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Local
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Data
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Preço
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Organizador ID
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-medium text-muted-foreground uppercase tracking-wider">
                        Ações
                      </th>
                    </tr>
                  </thead>
                  <tbody className="divide-y divide-border">
                    {paginatedEvents.map((event) => (
                      <tr key={event.id} className="hover:bg-muted/50 transition">
                        <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-foreground">
                          #{event.id}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="text-sm font-medium text-foreground">{event.nome}</div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center gap-2 text-sm text-muted-foreground">
                            <MapPin size={14} />
                            {event.local}
                          </div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center gap-2 text-sm text-muted-foreground">
                            <Calendar size={14} />
                            {new Date(event.data).toLocaleString("pt-BR", {
                              day: "2-digit",
                              month: "2-digit",
                              year: "numeric",
                              hour: "2-digit",
                              minute: "2-digit",
                            })}
                          </div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm">
                          {event.preco === 0 ? (
                            <span className="text-green-600 font-semibold">GRATUITO</span>
                          ) : (
                            <span className="font-semibold">
                              {new Intl.NumberFormat("pt-BR", {
                                style: "currency",
                                currency: "BRL",
                              }).format(event.preco)}
                            </span>
                          )}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap">
                          <div className="flex items-center gap-2 text-sm text-muted-foreground">
                            <User size={14} />
                            ID: {event.idUser}
                          </div>
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm">
                          <div className="flex items-center gap-2">
                            <button
                              onClick={() => handleEditClick(event)}
                              className="p-2 text-blue-600 hover:bg-blue-50 dark:hover:bg-blue-950 rounded transition"
                              title="Editar evento"
                            >
                              <Edit size={18} />
                            </button>
                            <button
                              onClick={() => handleDelete(event.id)}
                              className="p-2 text-red-600 hover:bg-red-50 dark:hover:bg-red-950 rounded transition"
                              title="Deletar evento"
                            >
                              <Trash2 size={18} />
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>

              {/* Paginação */}
              {totalPages > 1 && (
                <div className="flex items-center justify-center gap-2 mt-6">
                  <button
                    onClick={() => handlePageChange(currentPage - 1)}
                    disabled={currentPage === 0}
                    className="px-4 py-2 bg-card border border-border rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-accent transition"
                  >
                    <ChevronLeft size={20} />
                  </button>

                  <div className="flex gap-2">
                    {Array.from({ length: totalPages }, (_, i) => (
                      <button
                        key={i}
                        onClick={() => handlePageChange(i)}
                        className={`px-4 py-2 rounded-lg transition ${
                          currentPage === i
                            ? "bg-primary text-primary-foreground"
                            : "bg-card border border-border hover:bg-accent"
                        }`}
                      >
                        {i + 1}
                      </button>
                    ))}
                  </div>

                  <button
                    onClick={() => handlePageChange(currentPage + 1)}
                    disabled={currentPage === totalPages - 1}
                    className="px-4 py-2 bg-card border border-border rounded-lg disabled:opacity-50 disabled:cursor-not-allowed hover:bg-accent transition"
                  >
                    <ChevronRight size={20} />
                  </button>
                </div>
              )}
            </>
          ) : (
            <div className="bg-card rounded-lg p-12 border border-border text-center">
              <Calendar size={48} className="mx-auto mb-4 text-muted-foreground" />
              <p className="text-muted-foreground">Nenhum evento encontrado</p>
            </div>
          )}
        </main>
      </div>

      {editingEvent && (
        <EditEventModal
          event={editingEvent}
          onClose={() => setEditingEvent(null)}
          onEventUpdated={handleEventUpdated}
        />
      )}
    </div>
  )
}
