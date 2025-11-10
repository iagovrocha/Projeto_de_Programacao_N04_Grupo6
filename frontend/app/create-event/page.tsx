"use client"

import type React from "react"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Sidebar from "@/components/sidebar"
import Navbar from "@/components/navbar"

interface User {
  id: string
  nome: string
  email: string
  role: string
}

export default function CreateEventPage() {
  const [user, setUser] = useState<User | null>(null)
  const [loading, setLoading] = useState(false)
  const [formData, setFormData] = useState({
    nome: "",
    local: "",
    data: "",
    preco: "",
  })
  const [message, setMessage] = useState("")
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
  }, [router])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setMessage("")

    try {
      // Converter data para LocalDateTime format (yyyy-MM-ddTHH:mm:ss)
      const dataFormatada = formData.data.length === 16 ? `${formData.data}:00` : formData.data

      const response = await fetch("http://localhost:8080/eventos", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nome: formData.nome,
          local: formData.local,
          data: dataFormatada,
          idOrganizador: Number.parseInt(user!.id),
          preco: formData.preco ? Number.parseFloat(formData.preco) : 0,
        }),
      })

      if (response.ok) {
        setMessage("Evento criado com sucesso!")
        setFormData({
          nome: "",
          local: "",
          data: "",
          preco: "",
        })
        setTimeout(() => {
          router.push("/manage-events")
        }, 1500)
      } else {
        setMessage("Falha ao criar evento")
      }
    } catch (err) {
      setMessage("Erro ao criar evento")
      console.error("Error:", err)
    } finally {
      setLoading(false)
    }
  }

  if (!user) {
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
          <div className="max-w-2xl">
            <h2 className="text-2xl font-bold text-foreground mb-6">Criar Novo Evento</h2>

            <form onSubmit={handleSubmit} className="bg-card rounded-lg p-6 border border-border">
              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-foreground mb-2">Nome do Evento</label>
                  <input
                    type="text"
                    name="nome"
                    value={formData.nome}
                    onChange={handleChange}
                    className="w-full px-4 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
                    placeholder="Digite o nome do evento"
                    required
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-foreground mb-2">Local</label>
                  <input
                    type="text"
                    name="local"
                    value={formData.local}
                    onChange={handleChange}
                    className="w-full px-4 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
                    placeholder="Digite o local do evento"
                    required
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-foreground mb-2">Data e Hora</label>
                  <input
                    type="datetime-local"
                    name="data"
                    value={formData.data}
                    onChange={handleChange}
                    className="w-full px-4 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
                    required
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-foreground mb-2">Preço (R$)</label>
                  <input
                    type="number"
                    name="preco"
                    value={formData.preco}
                    onChange={handleChange}
                    min="0"
                    step="0.01"
                    className="w-full px-4 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
                    placeholder="0.00"
                  />
                  <p className="text-xs text-muted-foreground mt-1">Deixe em branco para evento gratuito</p>
                </div>

                {message && (
                  <div
                    className={`p-4 rounded-lg text-center ${
                      message.includes("success")
                        ? "bg-green-50 text-green-700 border border-green-200"
                        : "bg-red-50 text-red-700 border border-red-200"
                    }`}
                  >
                    {message}
                  </div>
                )}

                <button
                  type="submit"
                  disabled={loading}
                  className="w-full py-2 px-4 bg-primary text-primary-foreground rounded-lg font-medium hover:opacity-90 disabled:opacity-50"
                >
                  {loading ? "Criando..." : "Criar Evento"}
                </button>
              </div>
            </form>
          </div>
        </main>
      </div>
    </div>
  )
}
