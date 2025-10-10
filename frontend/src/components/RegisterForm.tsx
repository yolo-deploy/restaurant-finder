import React, { useState } from 'react'
import { Link } from 'react-router-dom'
import { api } from '../lib/http'

export default function RegisterForm() {
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
        if (password.length < 5) next.password = 'Passwort muss mind. 5 Zeichen haben.'
        setErrors(next)
        return Object.keys(next).length === 0
    }

    async function handleSubmit(e: React.FormEvent) {
        e.preventDefault()
        if (!validate()) {
            setNotification({type: 'error', text: 'Bitte korrigiere die markierten Felder.'})
            return
        }
        setLoading(true)
        setNotification({type: 'info', text: 'Registrierung wird ausgeführt…'})
        try {
            const resp = await api.post('/api/v1/user/create', { email, password })
            if (resp.status < 200 || resp.status >= 300) {
                setNotification({ type: 'error', text: `Registrierung fehlgeschlagen (HTTP ${resp.status}).` })
                return
            }
            setNotification({type: 'success', text: `Registrierung erfolgreich für: ${email}`})
            setEmail('')
            setPassword('')
            setErrors({})
        } catch (err: any) {
            // Axios-Error Handling
            const data = err?.response?.data
            const status = err?.response?.status
            if (data && typeof data === 'object') {
                const apiErrors: Record<string, string> = {}
                // mögliche Struktur: { errors: { email: 'bereits vergeben' }, message: '...' }
                if (data.errors && typeof data.errors === 'object') {
                    Object.entries(data.errors as Record<string, any>).forEach(([k, v]) => {
                        if (typeof v === 'string') apiErrors[k] = v
                    })
                }
                if (Object.keys(apiErrors).length > 0) setErrors(apiErrors)
                const msg = (data.message as string) || `Registrierung fehlgeschlagen${status ? ` (HTTP ${status})` : ''}.`
                setNotification({ type: 'error', text: msg })
            } else {
                setNotification({type: 'error', text: 'Netzwerkfehler. Bitte später erneut versuchen.'})
            }
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="container">
            {/* Notification Container */}
            <div
                id="notification"
                className={`message${notification ? ' ' + notification.type : ''}`}
                style={{display: notification ? 'block' : 'none'}}
            >
                {notification?.text}
            </div>

            <form id="registerForm" className="registration-form" onSubmit={handleSubmit} noValidate>
                <h1>Registrierung</h1>

                <div className="form-group">
                    <input
                        type="email"
                        name="email"
                        id="email"
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
                        name="password"
                        id="password"
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
                    {loading ? 'Registriere…' : 'Registrieren'}
                </button>

                <div className="login-link">
                    Bereits registriert? <Link to="/login">Hier anmelden</Link>
                </div>
            </form>
        </div>
    )
}