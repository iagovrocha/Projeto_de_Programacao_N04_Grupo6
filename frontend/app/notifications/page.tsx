"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"

interface Notification {
  idNotificacao: string
  mensagem: string
  tipo: string
  tag?: string
  createdAt: string
  lida: boolean
}

interface User {
  id: string
  name: string
  email: string
  role: string
}

export default function NotificationsPage() {
  const [user, setUser] = useState<User | null>(null)
  const [notifications, setNotifications] = useState<Notification[]>([])
  const [filteredNotifications, setFilteredNotifications] = useState<Notification[]>([])
  const [filterType, setFilterType] = useState("ALL")
  const [filterTag, setFilterTag] = useState("ALL")
  const [loading, setLoading] = useState(true)
  const router = useRouter()

  useEffect(() => {
    const userStr = localStorage.getItem("user")

    if (!userStr) {
      router.push("/login")
      return
    }

    const parsedUser = JSON.parse(userStr)
    setUser(parsedUser)
    fetchNotifications(parsedUser.id)
  }, [router])

  const fetchNotifications = async (userId: string) => {
    try {
      const response = await fetch(`http://localhost:8080/notificacao/byUser/${userId}`)
      if (response.ok) {
        const data = await response.json()
        setNotifications(data)
        setFilteredNotifications(data)
      }
    } catch (err) {
      console.error("Failed to fetch notifications:", err)
    } finally {
      setLoading(false)
    }
  }

  const applyFilters = () => {
    let filtered = notifications

    if (filterType !== "ALL") {
      filtered = filtered.filter((n) => n.tipo === filterType)
    }

    if (filterTag !== "ALL") {
      filtered = filtered.filter((n) => n.tag === filterTag)
    }

    setFilteredNotifications(filtered)
  }

  useEffect(() => {
    applyFilters()
  }, [filterType, filterTag, notifications])

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
      setNotifications(notifications.map((n) => (n.idNotificacao === notificationId ? { ...n, lida: true } : n)))
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
        <Navbar userName={user.name} />
        <main className="flex-1 overflow-auto p-6 bg-background">
          <div className="bg-card rounded-lg p-6 border border-border shadow-sm">
            <h2 className="text-2xl font-bold text-foreground mb-6">All Notifications</h2>

            <div className="flex gap-4 mb-6 flex-wrap">
              <div>
                <label className="block text-sm font-medium text-foreground mb-2">Filter by Type</label>
                <select
                  value={filterType}
                  onChange={(e) => setFilterType(e.target.value)}
                  className="px-4 py-2 border border-border rounded-lg bg-background text-foreground"
                >
                  <option value="ALL">All Types</option>
                  <option value="LEMBRETE">Lembrete</option>
                  <option value="ALERTA">Alerta</option>
                  <option value="CONFIRMACAO">Confirmação</option>
                  <option value="AVISO">Aviso</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-foreground mb-2">Filter by Tag</label>
                <select
                  value={filterTag}
                  onChange={(e) => setFilterTag(e.target.value)}
                  className="px-4 py-2 border border-border rounded-lg bg-background text-foreground"
                >
                  <option value="ALL">All Tags</option>
                  <option value="URGENT">Urgent</option>
                  <option value="REMINDER">Reminder</option>
                  <option value="INFO">Info</option>
                </select>
              </div>
            </div>

            {filteredNotifications.length > 0 ? (
              <div className="space-y-4">
                {filteredNotifications.map((notif) => (
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
                    <div className="flex items-start justify-between">
                      <div className="flex-1">
                        <p className="text-foreground font-medium">{notif.mensagem}</p>
                        <div className="flex gap-2 mt-2">
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
                            <span className="text-xs bg-primary/20 px-2 py-1 rounded text-primary">{notif.tag}</span>
                          )}
                        </div>
                        <span className="text-xs text-muted-foreground mt-2 block">
                          {new Date(notif.createdAt).toLocaleString()}
                        </span>
                      </div>
                      {!notif.lida && <div className="w-3 h-3 bg-primary rounded-full mt-1 flex-shrink-0 ml-4"></div>}
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-center text-muted-foreground py-12">No notifications found</p>
            )}
          </div>
        </main>
      </div>
    </div>
  )
}
