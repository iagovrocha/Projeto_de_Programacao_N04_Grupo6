"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"
import { Calendar, MapPin, Users, Trash2 } from "lucide-react"

interface Event {
  id: string
  title: string
  description: string
  date: string
  location: string
  capacity: number
  enrolledCount: number
}

interface User {
  id: string
  name: string
  email: string
  role: string
}

export default function SubscriptionsPage() {
  const [user, setUser] = useState<User | null>(null)
  const [events, setEvents] = useState<Event[]>([])
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
    fetchSubscriptions(parsedUser.id)
  }, [router])

  const fetchSubscriptions = async (userId: string) => {
    try {
      const response = await fetch(`http://localhost:8080/api/user/subscriptions?userId=${userId}`)
      if (response.ok) {
        const data = await response.json()
        setEvents(data)
      }
    } catch (err) {
      console.error("Failed to fetch subscriptions:", err)
    } finally {
      setLoading(false)
    }
  }

  const handleUnenroll = async (eventId: string) => {
    const userStr = localStorage.getItem("user")
    if (!userStr) return

    const parsedUser = JSON.parse(userStr)
    try {
      const response = await fetch(`http://localhost:8080/api/events/${eventId}/unenroll?userId=${parsedUser.id}`, {
        method: "POST",
      })
      if (response.ok) {
        alert("Successfully unrolled from event")
        fetchSubscriptions(parsedUser.id)
      }
    } catch (err) {
      console.error("Failed to unenroll:", err)
      alert("Failed to unenroll from event")
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
          <div className="mb-6">
            <h2 className="text-2xl font-bold text-foreground">My Events</h2>
            <p className="text-muted-foreground mt-2">Events you are enrolled in</p>
          </div>

          {events.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {events.map((event) => (
                <div
                  key={event.id}
                  className="bg-card rounded-lg overflow-hidden border border-border shadow-sm hover:shadow-lg transition"
                >
                  <div className="p-6">
                    <h3 className="text-lg font-bold text-foreground mb-2">{event.title}</h3>
                    <p className="text-sm text-muted-foreground mb-4">{event.description}</p>

                    <div className="space-y-2 mb-4">
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <Calendar size={16} />
                        {new Date(event.date).toLocaleDateString()}
                      </div>
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <MapPin size={16} />
                        {event.location}
                      </div>
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <Users size={16} />
                        {event.enrolledCount} / {event.capacity} enrolled
                      </div>
                    </div>

                    <button
                      onClick={() => handleUnenroll(event.id)}
                      className="w-full py-2 px-4 bg-destructive text-destructive-foreground rounded-lg font-medium hover:opacity-90 transition flex items-center justify-center gap-2"
                    >
                      <Trash2 size={16} />
                      Unenroll
                    </button>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-card rounded-lg p-12 border border-border text-center">
              <p className="text-muted-foreground mb-4">You are not enrolled in any events yet</p>
              <a
                href="/events"
                className="inline-block px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90"
              >
                Browse Events
              </a>
            </div>
          )}
        </main>
      </div>
    </div>
  )
}
