"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"
import { Calendar, MapPin } from "lucide-react"

interface Event {
  id: string
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

export default function EventsPage() {
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
    fetchEvents()
  }, [router])

  const fetchEvents = async () => {
    try {
      const response = await fetch("http://localhost:8080/eventos")
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

  const handleEnroll = async (eventId: string) => {
    const userStr = localStorage.getItem("user")
    if (!userStr) return

    const parsedUser = JSON.parse(userStr)
    try {
      const response = await fetch("http://localhost:8080/eventos/inscrever", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          idUsuario: Number.parseInt(parsedUser.id),
          idEvento: Number.parseInt(eventId),
        }),
      })

      if (response.ok) {
        const data = await response.json()
        alert(`Inscrição realizada com sucesso! ${data.mensagem || ''}`)
        fetchEvents()
      } else {
        const errorText = await response.text()
        alert(`Erro ao inscrever: ${errorText || 'Falha ao realizar inscrição'}`)
      }
    } catch (err) {
      console.error("Failed to enroll:", err)
      alert("Erro ao inscrever no evento")
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
            <h2 className="text-2xl font-bold text-foreground">Eventos Disponíveis</h2>
            <p className="text-muted-foreground mt-2">Navegue e inscreva-se nos próximos eventos</p>
          </div>

          {events.length > 0 ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {events.map((event) => (
                <div
                  key={event.id}
                  className="bg-card rounded-lg overflow-hidden border border-border shadow-sm hover:shadow-lg transition"
                >
                  <div className="p-6">
                    <h3 className="text-lg font-bold text-foreground mb-4">{event.nome}</h3>

                    <div className="space-y-3 mb-4">
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <Calendar size={16} />
                        <span>{new Date(event.data).toLocaleDateString('pt-BR', { 
                          day: '2-digit', 
                          month: '2-digit', 
                          year: 'numeric',
                          hour: '2-digit',
                          minute: '2-digit'
                        })}</span>
                      </div>
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <MapPin size={16} />
                        <span>{event.local}</span>
                      </div>
                    </div>

                    <div className="mb-4 pb-4 border-b border-border">
                      {event.preco > 0 ? (
                        <div className="text-center">
                          <span className="text-2xl font-bold text-primary">
                            {new Intl.NumberFormat('pt-BR', {
                              style: 'currency',
                              currency: 'BRL'
                            }).format(event.preco)}
                          </span>
                        </div>
                      ) : (
                        <div className="text-center">
                          <span className="text-2xl font-bold text-green-600">GRATUITO</span>
                        </div>
                      )}
                    </div>

                    <button
                      onClick={() => handleEnroll(event.id)}
                      className="w-full py-2 px-4 rounded-lg font-medium transition bg-primary text-primary-foreground hover:opacity-90"
                    >
                      Inscrever-se
                    </button>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <div className="bg-card rounded-lg p-12 border border-border text-center">
              <p className="text-muted-foreground mb-4">Nenhum evento disponível no momento</p>
            </div>
          )}
        </main>
      </div>
    </div>
  )
}
