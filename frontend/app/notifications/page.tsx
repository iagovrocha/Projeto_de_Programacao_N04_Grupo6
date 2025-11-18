"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"

interface Notification {
  idNotificacao: string
  titulo: string
  mensagem: string
  horario: string
  lida: boolean
  tipo: string
  tag?: string
}

interface User {
  id: string
  nome: string
  email: string
  role: string
}

export default function NotificationsPage() {
  const [user, setUser] = useState<User | null>(null)
  const [allNotifications, setAllNotifications] = useState<Notification[]>([])
  const [filteredNotifications, setFilteredNotifications] = useState<Notification[]>([])
  const [paginatedNotifications, setPaginatedNotifications] = useState<Notification[]>([])
  const [filterType, setFilterType] = useState("ALL")
  const [filterTag, setFilterTag] = useState("ALL")
  const [loading, setLoading] = useState(true)
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const itemsPerPage = 5
  const router = useRouter()

  useEffect(() => {
    const userStr = localStorage.getItem("user")

    if (!userStr) {
      router.push("/login")
      return
    }

    const parsedUser = JSON.parse(userStr)
    setUser(parsedUser)
    fetchAllNotifications(parsedUser.id)
  }, [router])

  useEffect(() => {
    applyFiltersAndPagination()
  }, [allNotifications, filterType, filterTag, currentPage])

  const fetchAllNotifications = async (userId: string) => {
    setLoading(true)
    try {
      // Buscar todas as notificações (tamanho grande para pegar todas)
      const response = await fetch(
        `http://localhost:8080/notificacao/reciveByUser/${userId}?page=0&size=1000&sort=idNotificacaoUsuario.idNotificacao,desc`
      )
      if (response.ok) {
        const data = await response.json()
        const notificationsList = data.content || []
        setAllNotifications(notificationsList)
      }
    } catch (err) {
      console.error("Failed to fetch notifications:", err)
    } finally {
      setLoading(false)
    }
  }

  const applyFiltersAndPagination = () => {
    let filtered = allNotifications

    // Aplicar filtros
    if (filterType !== "ALL") {
      filtered = filtered.filter((n) => n.tipo === filterType)
    }

    if (filterTag !== "ALL") {
      filtered = filtered.filter((n) => n.tag === filterTag)
    }

    setFilteredNotifications(filtered)

    // Calcular paginação
    const totalPgs = Math.ceil(filtered.length / itemsPerPage)
    setTotalPages(totalPgs)

    // Paginar resultados
    const startIndex = currentPage * itemsPerPage
    const endIndex = startIndex + itemsPerPage
    setPaginatedNotifications(filtered.slice(startIndex, endIndex))
  }

  useEffect(() => {
    // Reset para primeira página ao mudar filtros
    setCurrentPage(0)
  }, [filterType, filterTag])

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

  const handleMarkAsRead = async (notificationId: string) => {
    if (!user) return
    
    try {
      await fetch(`http://localhost:8080/notificacao`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          idNotificacao: parseInt(notificationId),
          idUser: parseInt(user.id),
        }),
      })
      setAllNotifications(allNotifications.map((n) => (n.idNotificacao === notificationId ? { ...n, lida: true } : n)))
    } catch (err) {
      console.error("Failed to mark notification as read:", err)
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
          <div className="bg-card rounded-lg p-6 border border-border shadow-sm">
            <h2 className="text-2xl font-bold text-foreground mb-6">Todas as Notificações</h2>

            <div className="flex gap-4 mb-6 flex-wrap">
              <div>
                <label className="block text-sm font-medium text-foreground mb-2">Filtrar por Tipo</label>
                <select
                  value={filterType}
                  onChange={(e) => setFilterType(e.target.value)}
                  className="px-4 py-2 border border-border rounded-lg bg-background text-foreground"
                >
                  <option value="ALL">Todos os Tipos</option>
                  <option value="LEMBRETE">Lembrete</option>
                  <option value="ALERTA">Alerta</option>
                  <option value="CONFIRMACAO">Confirmação</option>
                  <option value="AVISO">Aviso</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-foreground mb-2">Filtrar por Tag</label>
                <select
                  value={filterTag}
                  onChange={(e) => setFilterTag(e.target.value)}
                  className="px-4 py-2 border border-border rounded-lg bg-background text-foreground"
                >
                  <option value="ALL">Todas as Tags</option>
                  <option value="URGENT">Urgente</option>
                  <option value="REMINDER">Lembrete</option>
                  <option value="INFO">Informação</option>
                </select>
              </div>
            </div>

            {paginatedNotifications.length > 0 ? (
              <>
                <div className="space-y-4">
                  {paginatedNotifications.map((notif) => (
                    <div
                      key={notif.idNotificacao}
                      onClick={() => handleMarkAsRead(notif.idNotificacao)}
                      className={`p-4 rounded-lg border-l-4 cursor-pointer transition hover:shadow-md ${
                        notif.tipo === "ALERTA"
                          ? "border-l-red-500 bg-red-50 hover:bg-red-100"
                          : notif.tipo === "AVISO"
                            ? "border-l-yellow-500 bg-yellow-50 hover:bg-yellow-100"
                            : notif.tipo === "CONFIRMACAO"
                              ? "border-l-green-500 bg-green-50 hover:bg-green-100"
                              : "border-l-blue-500 bg-blue-50 hover:bg-blue-100"
                      } ${!notif.lida ? "opacity-100" : "opacity-75"}`}
                    >
                      <div className="flex items-start justify-between mb-2">
                        <h3 className="text-foreground font-bold text-lg">{notif.titulo}</h3>
                        {notif.lida ? (
                          <span className="text-xs px-2 py-1 rounded bg-gray-200 text-gray-700 flex-shrink-0">Lida</span>
                        ) : (
                          <span className="text-xs px-2 py-1 rounded bg-blue-500 text-white font-medium flex-shrink-0">Nova</span>
                        )}
                      </div>
                      <p className="text-foreground mb-3">{notif.mensagem}</p>
                      <div className="flex items-center gap-2 flex-wrap">
                        <span className={`text-xs px-2 py-1 rounded font-medium ${
                          notif.tipo === "ALERTA"
                            ? "bg-red-200 text-red-800"
                            : notif.tipo === "AVISO"
                              ? "bg-yellow-200 text-yellow-800"
                              : notif.tipo === "CONFIRMACAO"
                                ? "bg-green-200 text-green-800"
                                : "bg-blue-200 text-blue-800"
                        }`}>
                          {notif.tipo}
                        </span>
                        {notif.tag && (
                          <span className="text-xs px-2 py-1 rounded bg-purple-200 text-purple-800 font-medium">
                            {notif.tag}
                          </span>
                        )}
                        <span className="text-xs text-muted-foreground ml-auto">
                          {new Date(notif.horario).toLocaleString('pt-BR', {
                            day: '2-digit',
                            month: '2-digit',
                            year: 'numeric',
                            hour: '2-digit',
                            minute: '2-digit'
                          })}
                        </span>
                      </div>
                    </div>
                  ))}
                </div>

                {totalPages > 1 && (
                  <div className="mt-6 flex items-center justify-between border-t border-border pt-4">
                    <div className="text-sm text-muted-foreground">
                      Mostrando {currentPage * itemsPerPage + 1} - {Math.min((currentPage + 1) * itemsPerPage, filteredNotifications.length)} de {filteredNotifications.length} notificações
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
              <p className="text-center text-muted-foreground py-12">Nenhuma notificação encontrada</p>
            )}
          </div>
        </main>
      </div>
    </div>
  )
}
