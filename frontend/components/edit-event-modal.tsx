"use client"

import { useState, useEffect } from "react"
import { X } from "lucide-react"

interface Event {
  id: string | number
  nome: string
  local: string
  data: string
  idUser: number
}

interface EditEventModalProps {
  event: Event
  isOpen?: boolean
  onClose: () => void
  onEventUpdated: () => void
}

export default function EditEventModal({ event, isOpen = true, onClose, onEventUpdated }: EditEventModalProps) {
  const [formData, setFormData] = useState({
    nome: "",
    local: "",
    data: "",
  })
  const [loading, setLoading] = useState(false)
  const [message, setMessage] = useState("")

  useEffect(() => {
    if (event) {
      const dataFormatada = event.data.substring(0, 16)
      setFormData({
        nome: event.nome,
        local: event.local,
        data: dataFormatada,
      })
    }
  }, [event])

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
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
    const dataFormatada = formData.data.length === 16 ? `${formData.data}:00` : formData.data

      const response = await fetch(`http://localhost:8080/eventos/${event.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nome: formData.nome,
          local: formData.local,
          data: dataFormatada,
        }),
      })

      if (response.ok) {
        setMessage("Evento atualizado com sucesso!")
        setTimeout(() => {
          onEventUpdated()
          onClose()
        }, 1500)
      } else {
        setMessage("Falha ao atualizar evento")
      }
    } catch (err) {
      setMessage("Erro ao atualizar evento")
      console.error("Error:", err)
    } finally {
      setLoading(false)
    }
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-card rounded-lg max-w-2xl w-full max-h-[90vh] overflow-y-auto border border-border shadow-xl">
        <div className="sticky top-0 bg-card border-b border-border p-6 flex justify-between items-center">
          <h2 className="text-2xl font-bold text-foreground">Editar Evento</h2>
          <button
            onClick={onClose}
            className="p-2 hover:bg-secondary rounded-lg text-foreground transition"
          >
            <X size={24} />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6">
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

            {message && (
              <div
                className={`p-4 rounded-lg text-center ${
                  message.includes("sucesso")
                    ? "bg-green-50 text-green-700 border border-green-200"
                    : "bg-red-50 text-red-700 border border-red-200"
                }`}
              >
                {message}
              </div>
            )}

            <div className="flex gap-3 pt-4">
              <button
                type="button"
                onClick={onClose}
                className="flex-1 py-2 px-4 bg-secondary text-foreground rounded-lg font-medium hover:opacity-90"
              >
                Cancelar
              </button>
              <button
                type="submit"
                disabled={loading}
                className="flex-1 py-2 px-4 bg-primary text-primary-foreground rounded-lg font-medium hover:opacity-90 disabled:opacity-50"
              >
                {loading ? "Atualizando..." : "Atualizar Evento"}
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  )
}
