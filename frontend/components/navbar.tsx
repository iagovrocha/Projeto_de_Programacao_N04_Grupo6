"use client"

import { useState, useEffect } from "react"
import { Bell } from "lucide-react"

interface Notification {
  idNotificacao: string
  mensagem: string
  tipo: string
  createdAt: string
  lida: boolean
}

interface User {
  id: string
  name: string
  email: string
  role: string
}

interface NavbarProps {
  userName: string
}

export default function Navbar({ userName }: NavbarProps) {
  const [user, setUser] = useState<User | null>(null)
  const [notifications, setNotifications] = useState<Notification[]>([])
  const [showDropdown, setShowDropdown] = useState(false)
  const [isHovering, setIsHovering] = useState(false)

  useEffect(() => {
    const userStr = localStorage.getItem("user")
    if (userStr) {
      const parsedUser = JSON.parse(userStr)
      setUser(parsedUser)
      fetchNotifications(parsedUser.id)
    }
  }, [])

  const fetchNotifications = async (userId: string) => {
    try {
      const response = await fetch(`http://localhost:8080/notificacao/byUser/${userId}`)
      if (response.ok) {
        const data = await response.json()
        setNotifications(data)
      }
    } catch (err) {
      console.error("Failed to fetch notifications:", err)
    }
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
      setNotifications(notifications.map((n) => (n.idNotificacao === notificationId ? { ...n, lida: true } : n)))
    } catch (err) {
      console.error("Failed to mark notification as read:", err)
    }
  }

  const unreadCount = notifications.filter((n) => !n.lida).length

  return (
    <nav className="bg-card border-b border-border px-6 py-4 flex items-center justify-between">
      <div className="flex-1">
        <h2 className="text-lg font-semibold text-foreground">Welcome back, {userName}</h2>
      </div>

      <div className="relative" onMouseLeave={() => setIsHovering(false)}>
        <button
          onClick={() => setShowDropdown(!showDropdown)}
          onMouseEnter={() => setIsHovering(true)}
          className="relative p-2 hover:bg-secondary rounded-lg transition"
        >
          <Bell size={24} className="text-foreground" />
          {unreadCount > 0 && (
            <span className="absolute top-0 right-0 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
              {unreadCount}
            </span>
          )}
        </button>

        {(showDropdown || isHovering) && (
          <div className="absolute right-0 mt-2 w-80 bg-card border border-border rounded-lg shadow-lg z-50">
            <div className="p-4 border-b border-border">
              <h3 className="font-semibold text-foreground">Recent Notifications</h3>
            </div>

            {notifications.length > 0 ? (
              <div className="max-h-96 overflow-y-auto">
                {notifications.map((notif) => (
                  <div
                    key={notif.idNotificacao}
                    className={`p-3 border-b border-border hover:bg-secondary/50 transition cursor-pointer flex justify-between items-start gap-2 ${
                      !notif.lida ? "bg-primary/5" : ""
                    }`}
                    onClick={() => handleMarkAsRead(notif.idNotificacao)}
                  >
                    <div className="flex-1">
                      <p className="text-sm text-foreground">{notif.mensagem}</p>
                      <span className="text-xs text-muted-foreground">
                        {new Date(notif.createdAt).toLocaleDateString()}
                      </span>
                    </div>
                    {!notif.lida && <div className="w-2 h-2 bg-primary rounded-full mt-1"></div>}
                  </div>
                ))}
              </div>
            ) : (
              <div className="p-4 text-center text-muted-foreground text-sm">No notifications yet</div>
            )}

            <a
              href="/notifications"
              className="block p-3 text-center text-primary hover:bg-secondary/50 text-sm font-medium border-t border-border"
            >
              View All Notifications
            </a>
          </div>
        )}
      </div>
    </nav>
  )
}
