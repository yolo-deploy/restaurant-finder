import React, { useState } from 'react'
import { Link } from 'react-router-dom'

export default function LoginForm() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [errors, setErrors] = useState<Record<string, string>>({})
  const [loading, setLoading] = useState(false)
  const [notification, setNotification] = useState<{ type: 'error' | 'success' | 'info'; text: string } | null>(null)

  function validate() {
    const next: Record<string, string> = {}
    if (!email.trim()) {
      next.email = 'Bitte E-Mail eingeben.'
    } else if (!/^([^\s@]+)@([^\s@]+)\.[^\s@]+$/.test(email)) {
      next.email = 'Bitte eine gültige E-Mail angeben.'
    }
    if (!password) next.password = 'Bitte Passwort eingeben.'
    setErrors(next)
    return Object.keys(next).length === 0
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!validate()) {
      setNotification({ type: 'error', text: 'Bitte korrigiere die markierten Felder.' })
      return
    }
    setLoading(true)
    setNotification({ type: 'info', text: 'Login wird ausgeführt…' })
    try {
      const resp = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
      })
      if (!resp.ok) throw new Error('HTTP ' + resp.status)
      await resp.json().catch(() => ({}))
      setNotification({ type: 'success', text: 'Login erfolgreich.' })
      setPassword('')
    } catch (err) {
      setNotification({ type: 'error', text: 'Login fehlgeschlagen. Bitte erneut versuchen.' })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container">
      <div
        id="notification"
        className={`message${notification ? ' ' + notification.type : ''}`}
        style={{ display: notification ? 'block' : 'none' }}
      >
        {notification?.text}
      </div>

      <form className="login-form" onSubmit={handleSubmit} noValidate>
        <h1>Login</h1>

        <div className="form-group">
          <input
            type="email"
            id="email"
            name="email"
            required
            placeholder=" "
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            aria-invalid={Boolean(errors.email)}
          />
          <label htmlFor="email">E-Mail</label>
          {errors.email && <span className="error-message" id="email-error">{errors.email}</span>}
        </div>

        <div className="form-group">
          <input
            type="password"
            id="password"
            name="password"
            required
            placeholder=" "
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            aria-invalid={Boolean(errors.password)}
          />
          <label htmlFor="password">Passwort</label>
          {errors.password && <span className="error-message" id="password-error">{errors.password}</span>}
        </div>

        <button type="submit" className="submit-btn" disabled={loading}>
          {loading ? 'Anmelden…' : 'Anmelden'}
        </button>

        <div className="register-link">
          Kein Konto? <Link to="/register">Jetzt registrieren</Link>
        </div>
      </form>
    </div>
  )
}
