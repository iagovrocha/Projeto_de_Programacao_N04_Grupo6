"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"
import { Bell, User, Users, ChevronLeft, ChevronRight } from "lucide-react"

interface Notification {
  idNotificacao: number
  idUser: number | null
  titulo: string
  mensagem: string
  statusEnvio: boolean
  dataHorarioEnvio: string
  tipo: string
}

interface User {
  id: string
  nome: string
  email: string
  role: string
}

export default function AdminNotificationsPage() {
  const [user, setUser] = useState<User | null>(null)
  const [notifications, setNotifications] = useState<Notification[]>([])
  const [loading, setLoading] = useState(true)
  const [currentPage, setCurrentPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
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
    fetchAllNotifications(parsedUser.id, 0)
  }, [router])

  const fetchAllNotifications = async (userId: string, page: number) => {
    try {
      setLoading(true)
      const response = await fetch(
        `http://localhost:8080/notificacao/all/${userId}?page=${page}&size=10&sort=idNotificacao,desc`,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      )
      
      if (response.ok) {
        const data = await response.json()
        setNotifications(data.content)
        setTotalPages(data.totalPages)
        setCurrentPage(page)
      } else {
        console.error("Failed to fetch notifications:", response.status)
      }
    } catch (err) {
      console.error("Failed to fetch notifications:", err)
    } finally {
      setLoading(false)
    }
  }

  const handlePageChange = (newPage: number) => {
    if (user && newPage >= 0 && newPage < totalPages) {
      fetchAllNotifications(user.id, newPage)
    }
  }

  const getTypeColor = (tipo: string) => {
    switch (tipo) {
      case "ALERTA":
        return "bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200"
      case "AVISO":
        return "bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200"
      case "CONFIRMACAO":
        return "bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200"
      case "LEMBRETE":
        return "bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200"
      default:
        return "bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200"
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
            <h2 className="text-2xl font-bold text-foreground">Todas as Notificações</h2>
            <p className="text-muted-foreground mt-2">Visualize todas as notificações do sistema</p>
          </div>

          {notifications.length > 0 ? (
            <>
              <div className="space-y-4">
                {notifications.map((notification) => (
                  <div
                    key={notification.idNotificacao}
                    className="bg-card rounded-lg p-6 border border-border shadow-sm hover:shadow-md transition"
                  >
                    <div className="flex items-start justify-between mb-3">
                      <div className="flex items-center gap-3">
                        <Bell className="text-primary" size={20} />
                        <div>
                          <h3 className="font-semibold text-foreground">{notification.titulo}</h3>
                          <span className={`inline-block mt-1 px-2 py-1 rounded text-xs font-medium ${getTypeColor(notification.tipo)}`}>
                            {notification.tipo}
                          </span>
                        </div>
                      </div>
                      <div className="text-right text-sm">
                        <p className="text-muted-foreground">
                          {new Date(notification.dataHorarioEnvio).toLocaleString("pt-BR")}
                        </p>
                        <p className={`mt-1 ${notification.statusEnvio ? "text-green-600" : "text-red-600"}`}>
                          {notification.statusEnvio ? "Enviado" : "Pendente"}
                        </p>
                      </div>
                    </div>

                    <p className="text-muted-foreground mb-4 whitespace-pre-line">{notification.mensagem}</p>

                    <div className="flex items-center gap-4 text-sm border-t pt-3 mt-3">
                      <div className="flex items-center gap-2">
                        <User size={16} className="text-muted-foreground" />
                        <span className="text-muted-foreground">
                          ID Remetente: {notification.idUser || "Sistema"}
                        </span>
                      </div>
                      <div className="flex items-center gap-2">
                        <Bell size={16} className="text-muted-foreground" />
                        <span className="text-muted-foreground">ID: {notification.idNotificacao}</span>
                      </div>
                    </div>
                  </div>
                ))}
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
              <Bell size={48} className="mx-auto mb-4 text-muted-foreground" />
              <p className="text-muted-foreground">Nenhuma notificação encontrada no sistema</p>
            </div>
          )}
        </main>
      </div>
    </div>
  )
}
