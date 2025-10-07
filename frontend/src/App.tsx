import React, { useState } from 'react'
import './App.css'

export default function App() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [errors, setErrors] = useState<Record<string, string>>({})
  const [loading, setLoading] = useState(false)

  function validate() {
    const next: Record<string, string> = {}
    if (!username.trim()) next.username = 'Bitte Username eingeben.'
    if (!email.trim()) {
      next.email = 'Bitte E-Mail eingeben.'
    } else if (!/^([^\s@]+)@([^\s@]+)\.[^\s@]+$/.test(email)) {
      next.email = 'Bitte eine gültige E-Mail angeben.'
    }
    if (password.length < 6) next.password = 'Passwort muss mind. 6 Zeichen haben.'
    setErrors(next)
    return Object.keys(next).length === 0
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!validate()) return
    setLoading(true)
    try {
      // Hinweis: Per Vite-Proxy kann /api zu http://localhost:8080/restaurant-finder umgeschrieben werden
      // (Konfiguration in vite.config.ts erforderlich)
      const resp = await fetch('/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, password })
      })
      if (!resp.ok) throw new Error('HTTP ' + resp.status)
      await resp.json().catch(() => ({}))
      alert(`Registrierung erfolgreich für: ${username}`)
      setUsername('')
      setEmail('')
      setPassword('')
      setErrors({})
    } catch (err) {
      alert('Registrierung fehlgeschlagen. Bitte erneut versuchen.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="page">
      <div className="card">
        <div className="card-inner">
          <h1 className="title">Registrieren</h1>
          <p className="subtitle">Erstelle dein Konto, um favoriten zu speichern.</p>

          <form onSubmit={handleSubmit} noValidate>
            <div className="field">
              <label className="label" htmlFor="username">Username</label>
              <input
                id="username"
                className="input"
                type="text"
                placeholder="maxi123"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
              {errors.username && <div className="error">{errors.username}</div>}
            </div>

            <div className="field">
              <label className="label" htmlFor="email">E-Mail</label>
              <input
                id="email"
                className="input"
                type="email"
                placeholder="max@beispiel.de"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              {errors.email && <div className="error">{errors.email}</div>}
            </div>

            <div className="field">
              <label className="label" htmlFor="password">Passwort</label>
              <input
                id="password"
                className="input"
                type="password"
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              {errors.password && <div className="error">{errors.password}</div>}
            </div>

            <button type="submit" className="button" disabled={loading}>
              {loading ? 'Registriere...' : 'Registrieren'}
            </button>
          </form>

          <div className="actions">
            <span>Hast du schon ein Konto?</span>
            <a href="#login">Zum Login</a>
          </div>

          <div className="footer-hint">
            Mit deiner Registrierung akzeptierst du unsere <a href="#agb">AGB</a> und <a href="#privacy">Datenschutzrichtlinie</a>.
          </div>
        </div>
      </div>
    </div>
  )}
