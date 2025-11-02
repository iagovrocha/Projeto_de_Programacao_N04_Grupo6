"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"
import { Edit, Trash2, Users } from "lucide-react"

interface Event {
  id: string
  title: string
  description: string
  date: string
  location: string
  capacity: number
  enrolledCount: number
  category: string
}

interface User {
  id: string
  name: string
  email: string
  role: string
}

export default function ManageEventsPage() {
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
    if (parsedUser.role !== "ORGANIZER") {
      router.push("/dashboard")
      return
    }

    setUser(parsedUser)
    fetchEvents(parsedUser.id)
  }, [router])

  const fetchEvents = async (userId: string) => {
    try {
      const response = await fetch(`http://localhost:8080/organizer/events?userId=${userId}`)
      if (response.ok) {
        const data = await response.json()
        setEvents(data)
      }
    } catch (err) {
      console.error("Failed to fetch events:", err)
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (eventId: string) => {
    if (!window.confirm("Are you sure you want to delete this event?")) return

    try {
      const response = await fetch(`http://localhost:8080/events/${eventId}`, {
        method: "DELETE",
      })
      if (response.ok) {
        alert("Event deleted successfully")
        const userStr = localStorage.getItem("user")
        if (userStr) {
          const parsedUser = JSON.parse(userStr)
          fetchEvents(parsedUser.id)
        }
      }
    } catch (err) {
      console.error("Failed to delete event:", err)
      alert("Failed to delete event")
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
          <div className="flex justify-between items-center mb-6">
            <div>
              <h2 className="text-2xl font-bold text-foreground">Manage Events</h2>
              <p className="text-muted-foreground mt-2">Events you have organized</p>
            </div>
            <a
              href="/create-event"
              className="px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90 font-medium"
            >
              Create New Event
            </a>
          </div>

          {events.length > 0 ? (
            <div className="space-y-4">
              {events.map((event) => (
                <div
                  key={event.id}
                  className="bg-card rounded-lg p-6 border border-border flex items-center justify-between hover:shadow-lg transition"
                >
                  <div className="flex-1">
                    <h3 className="text-lg font-bold text-foreground">{event.title}</h3>
                    <p className="text-sm text-muted-foreground mt-1">{event.description}</p>
                    <div className="flex gap-4 mt-3 text-sm text-muted-foreground">
                      <span>{new Date(event.date).toLocaleDateString()}</span>
                      <span>{event.location}</span>
                      <div className="flex items-center gap-1">
                        <Users size={16} />
                        {event.enrolledCount} / {event.capacity}
                      </div>
                    </div>
                  </div>

                  <div className="flex gap-2 ml-4">
                    <button className="p-2 hover:bg-secondary rounded-lg text-foreground">
                      <Edit size={20} />
                    </button>
                    <button
                      onClick={() => handleDelete(event.id)}
                      className="p-2 hover:bg-destructive/10 rounded-lg text-destructive"
                    >
                      <Trash2 size={20} />
                    </button>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-card rounded-lg p-12 border border-border text-center">
              <p className="text-muted-foreground mb-4">You haven't created any events yet</p>
              <a
                href="/create-event"
                className="inline-block px-6 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90"
              >
                Create Your First Event
              </a>
            </div>
          )}
        </main>
      </div>
    </div>
  )
}
