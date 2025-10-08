import axios from 'axios'

const baseURL = (import.meta as any).env?.VITE_API_BASE_URL || ''

export const api = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json',
  },
})
