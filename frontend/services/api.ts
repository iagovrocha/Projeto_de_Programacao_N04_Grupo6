const API_BASE_URL = "http://localhost:8080/"

export async function apiCall(endpoint: string, options: RequestInit = {}) {
  const headers: HeadersInit = {
    "Content-Type": "application/json",
    ...options.headers,
  }

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers,
  })

  if (!response.ok) {
    throw new Error(`API Error: ${response.statusText}`)
  }

  return response.json()
}

export const notificationsService = {
  getRecent: (userId: string, limit = 5) => apiCall(`/notifications/recent?userId=${userId}&limit=${limit}`),
  getAll: (userId: string) => apiCall(`/notificacao/byUser/${userId}`),
  markAsRead: (id: string) => apiCall(`/notifications`, { method: "PUT" }),
}

export const eventsService = {
  getAvailable: () => apiCall("/events/available"),
  getMySubscriptions: (userId: string) => apiCall(`/user/subscriptions?userId=${userId}`),
  getOrganizerEvents: (userId: string) => apiCall(`/organizer/events?userId=${userId}`),
  create: (data: any) => apiCall("/events", { method: "POST", body: JSON.stringify(data) }),
  enroll: (id: string, userId: string) => apiCall(`/events/${id}/enroll?userId=${userId}`, { method: "POST" }),
  unenroll: (id: string, userId: string) => apiCall(`/events/${id}/unenroll?userId=${userId}`, { method: "POST" }),
  delete: (id: string) => apiCall(`/events/${id}`, { method: "DELETE" }),
}

export const authService = {
  login: (email: string, password: string) =>
    apiCall("/auth/login", {
      method: "POST",
      body: JSON.stringify({ email, password }),
    }),
  register: (data: any) =>
    apiCall("/auth/register", {
      method: "POST",
      body: JSON.stringify(data),
    }),
}
