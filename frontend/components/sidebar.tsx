"use client"

import { useState } from "react"
import Link from "next/link"
import { usePathname } from "next/navigation"
import { Bell, Home, Calendar, CheckCircle, Plus, Settings, LogOut, Menu, X } from "lucide-react"

interface SidebarProps {
  userType: string
}

export default function Sidebar({ userType }: SidebarProps) {
  const [isMinimized, setIsMinimized] = useState(false)
  const pathname = usePathname()

  const menuItems = [
    { href: "/dashboard", label: "Dashboard", icon: Home },
    { href: "/notifications", label: "Notifications", icon: Bell },
    { href: "/subscriptions", label: "My Events", icon: CheckCircle },
    { href: "/events", label: "Browse Events", icon: Calendar },
  ]

  const organizerItems = [
    { href: "/create-event", label: "Create Event", icon: Plus },
    { href: "/manage-events", label: "Manage Events", icon: Settings },
  ]

  const isActive = (href: string) => pathname === href

  return (
    <aside
      className={`fixed left-0 top-0 h-full bg-sidebar border-r border-sidebar-border transition-all duration-300 ${
        isMinimized ? "w-20" : "w-64"
      }`}
    >
      <div className="p-4 flex items-center justify-between">
        {!isMinimized && <h1 className="text-xl font-bold text-sidebar-foreground">Nova Aurora</h1>}
        <button onClick={() => setIsMinimized(!isMinimized)} className="p-2 hover:bg-sidebar-accent rounded">
          {isMinimized ? (
            <Menu size={20} className="text-sidebar-foreground" />
          ) : (
            <X size={20} className="text-sidebar-foreground" />
          )}
        </button>
      </div>

      <nav className="p-4 space-y-2">
        {menuItems.map((item) => {
          const Icon = item.icon
          const active = isActive(item.href)

          return (
            <Link
              key={item.href}
              href={item.href}
              className={`flex items-center gap-3 px-3 py-2 rounded-lg transition ${
                active
                  ? "bg-sidebar-primary text-sidebar-primary-foreground"
                  : "text-sidebar-foreground hover:bg-sidebar-accent"
              }`}
              title={isMinimized ? item.label : ""}
            >
              <Icon size={20} />
              {!isMinimized && <span className="text-sm">{item.label}</span>}
            </Link>
          )
        })}

        {userType === "ORGANIZER" && (
          <div className="pt-4 mt-4 border-t border-sidebar-border">
            {organizerItems.map((item) => {
              const Icon = item.icon
              const active = isActive(item.href)

              return (
                <Link
                  key={item.href}
                  href={item.href}
                  className={`flex items-center gap-3 px-3 py-2 rounded-lg transition ${
                    active
                      ? "bg-sidebar-primary text-sidebar-primary-foreground"
                      : "text-sidebar-foreground hover:bg-sidebar-accent"
                  }`}
                  title={isMinimized ? item.label : ""}
                >
                  <Icon size={20} />
                  {!isMinimized && <span className="text-sm">{item.label}</span>}
                </Link>
              )
            })}
          </div>
        )}
      </nav>

      <div className="absolute bottom-4 left-4 right-4">
        <button
          onClick={() => {
            localStorage.removeItem("user")
            window.location.href = "/login"
          }}
          className={`w-full flex items-center gap-3 px-3 py-2 rounded-lg text-red-600 hover:bg-red-50 transition ${
            isMinimized ? "justify-center" : ""
          }`}
        >
          <LogOut size={20} />
          {!isMinimized && <span className="text-sm">Logout</span>}
        </button>
      </div>
    </aside>
  )
}
