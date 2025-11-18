"use client"

import { useState, useEffect } from "react"
import { X } from "lucide-react"

interface Event {
  id: string
  nome: string
  local: string
  data: string
}

interface CreateNotificationModalProps {
  event: Event
  isOpen: boolean
  onClose: () => void
  onNotificationCreated: () => void
}

export default function CreateNotificationModal({
  event,
  isOpen,
  onClose,
  onNotificationCreated,
}: CreateNotificationModalProps) {
  const [formData, setFormData] = useState({
    titulo: "",
    mensagem: "",
    tipo: "AVISO",
  })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState("")
  const [inscritosCount, setInscritosCount] = useState(0)

  useEffect(() => {
    if (isOpen && event) {
      fetchInscritosCount()
      setFormData({
        titulo: `Notificação - ${event.nome}`,
        mensagem: "",
        tipo: "AVISO",
      })
    }
  }, [isOpen, event])

  const fetchInscritosCount = async () => {
    try {
      const response = await fetch(`http://localhost:8080/eventos/${event.id}/inscritos`)
      if (response.ok) {
        const inscritos = await response.json()
        setInscritosCount(inscritos.length)
      }
    } catch (err) {
      console.error("Erro ao buscar inscritos:", err)
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setLoading(true)
    setError("")

    try {
      const inscritosResponse = await fetch(
        `http://localhost:8080/eventos/${event.id}/inscritos`
      )
      
      if (!inscritosResponse.ok) {
        throw new Error("Erro ao buscar usuários inscritos")
      }

      const idsInscritos = await inscritosResponse.json()

      if (idsInscritos.length === 0) {
        setError("Este evento não possui usuários inscritos")
        setLoading(false)
        return
      }

      const notificationData = {
        titulo: formData.titulo,
        mensagem: formData.mensagem,
        tipo: formData.tipo,
        idRemetente: null,
        destinatarios: idsInscritos,
        idTag: null,
      }

      const response = await fetch("http://localhost:8080/notificacao", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(notificationData),
      })

      if (response.ok) {
        alert("Notificação enviada com sucesso para todos os inscritos!")
        onNotificationCreated()
        onClose()
      } else {
        const errorText = await response.text()
        throw new Error(errorText || "Erro ao criar notificação")
      }
    } catch (err: any) {
      console.error("Erro ao criar notificação:", err)
      setError(err.message || "Falha ao criar notificação")
    } finally {
      setLoading(false)
    }
  }

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div className="bg-card rounded-lg shadow-xl w-full max-w-md mx-4 border border-border">
        <div className="flex items-center justify-between p-4 border-b border-border">
          <h2 className="text-xl font-bold text-foreground">
            Criar Notificação
          </h2>
          <button
            onClick={onClose}
            className="p-1 hover:bg-secondary rounded-lg text-muted-foreground"
          >
            <X size={20} />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-4 space-y-4">
          <div>
            <label className="block text-sm font-medium text-foreground mb-2">
              Evento
            </label>
            <div className="p-3 bg-secondary rounded-lg">
              <p className="font-medium text-foreground">{event.nome}</p>
              <p className="text-sm text-muted-foreground mt-1">
                {inscritosCount} {inscritosCount === 1 ? "inscrito" : "inscritos"}
              </p>
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-foreground mb-2">
              Título *
            </label>
            <input
              type="text"
              value={formData.titulo}
              onChange={(e) =>
                setFormData({ ...formData, titulo: e.target.value })
              }
              className="w-full px-3 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
              placeholder="Digite o título da notificação"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-foreground mb-2">
              Mensagem *
            </label>
            <textarea
              value={formData.mensagem}
              onChange={(e) =>
                setFormData({ ...formData, mensagem: e.target.value })
              }
              className="w-full px-3 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
              placeholder="Digite a mensagem da notificação"
              rows={4}
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-foreground mb-2">
              Tipo *
            </label>
            <select
              value={formData.tipo}
              onChange={(e) =>
                setFormData({ ...formData, tipo: e.target.value })
              }
              className="w-full px-3 py-2 border border-border rounded-lg bg-background text-foreground focus:outline-none focus:ring-2 focus:ring-primary"
              required
            >
              <option value="AVISO">Aviso</option>
              <option value="CONFIRMACAO">Confirmação</option>
              <option value="LEMBRETE">Lembrete</option>
              <option value="CANCELAMENTO">Cancelamento</option>
            </select>
          </div>

          {error && (
            <div className="p-3 bg-destructive/10 border border-destructive rounded-lg">
              <p className="text-sm text-destructive">{error}</p>
            </div>
          )}

          <div className="flex gap-2 pt-2">
            <button
              type="button"
              onClick={onClose}
              className="flex-1 px-4 py-2 border border-border rounded-lg text-foreground hover:bg-secondary transition"
            >
              Cancelar
            </button>
            <button
              type="submit"
              disabled={loading}
              className="flex-1 px-4 py-2 bg-primary text-primary-foreground rounded-lg hover:opacity-90 transition disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {loading ? "Enviando..." : "Enviar Notificação"}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
