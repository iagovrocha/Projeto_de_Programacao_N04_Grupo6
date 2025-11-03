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

export default function Dashboard() {
  const [user, setUser] = useState<User | null>(null)
  const [notifications, setNotifications] = useState<Notification[]>([])
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
        setNotifications(data.slice(0, 10))
      }
    } catch (err) {
      console.error("Failed to fetch notifications:", err)
    } finally {
      setLoading(false)
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
          <div className="grid grid-cols-1 gap-6">
            <div className="bg-card rounded-lg p-6 border border-border shadow-sm">
              <h2 className="text-2xl font-bold text-foreground mb-4">Latest Notifications</h2>
              {notifications.length > 0 ? (
                <div className="space-y-3">
                  {notifications.map((notif) => (
                    <div
                      key={notif.idNotificacao}
                      className={`p-4 rounded-lg border-l-4 ${
                        notif.tipo === "ALERTA"
                          ? "border-l-red-500 bg-red-50"
                          : notif.tipo === "AVISO"
                            ? "border-l-yellow-500 bg-yellow-50"
                            : notif.tipo === "CONFIRMACAO"
                              ? "border-l-green-500 bg-green-50"
                              : "border-l-blue-500 bg-blue-50"
                      }`}
                    >
                      <p className="text-foreground font-medium">{notif.mensagem}</p>
                      <div className="flex items-center gap-2 mt-2">
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
                        <span className="text-xs text-muted-foreground">
                          {new Date(notif.createdAt).toLocaleDateString()}
                        </span>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <p className="text-muted-foreground">No notifications yet</p>
              )}
            </div>

            <div className="grid grid-cols-3 gap-4">
              <div className="bg-card rounded-lg p-6 border border-border shadow-sm">
                <h3 className="text-sm font-medium text-muted-foreground mb-2">My Events</h3>
                <p className="text-3xl font-bold text-primary">5</p>
              </div>
              <div className="bg-card rounded-lg p-6 border border-border shadow-sm">
                <h3 className="text-sm font-medium text-muted-foreground mb-2">Available Events</h3>
                <p className="text-3xl font-bold text-accent">12</p>
              </div>
              <div className="bg-card rounded-lg p-6 border border-border shadow-sm">
                <h3 className="text-sm font-medium text-muted-foreground mb-2">Unread Notifications</h3>
                <p className="text-3xl font-bold text-destructive">{notifications.filter((n) => !n.lida).length}</p>
              </div>
            </div>
          </div>
        </main>
      </div>
    </div>
  )
}
