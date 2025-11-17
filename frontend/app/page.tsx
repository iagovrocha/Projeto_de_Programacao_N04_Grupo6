"use client"

import { useState, useEffect } from "react"
import { useRouter } from "next/navigation"
import Login from "./login/page"

export default function Home() {
  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [loading, setLoading] = useState(true)
  const router = useRouter()

  useEffect(() => {
    const token = localStorage.getItem("authToken")
    if (token) {
      setIsAuthenticated(true)
      router.push("/dashboard")
    }
    setLoading(false)
  }, [router])

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-background">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary"></div>
      </div>
    )
  }

  return <Login />
}
