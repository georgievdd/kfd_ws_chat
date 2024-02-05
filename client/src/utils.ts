import axios, {InternalAxiosRequestConfig} from "axios";
export const apiHost = process.env.REACT_APP_API_HOST
export const apiPort = process.env.REACT_APP_API_PORT
const instance = axios.create({
    baseURL: `http://${apiHost}:${apiPort}`,
})
instance.interceptors.request.use((config: InternalAxiosRequestConfig): InternalAxiosRequestConfig => {
    config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`
    config.headers["ngrok-skip-browser-warning"] = true
    return config
})

export const showAlert = (message: string, variant?: 'alert-danger' | 'alert-success' | 'alert-warning') => {
    // Create a div element
    const alertDiv = document.createElement('div');

    // Apply class and message
    alertDiv.className = variant || 'alert-danger';
    alertDiv.innerText = message;

    // Append to body
    document.body.appendChild(alertDiv);

    // Remove after 3 seconds
    setTimeout(() => {
        document.body.removeChild(alertDiv);
    }, 2000);
}

export const signin = (email: string, password: string) =>
    instance.post("/users/signin", { email, password })
        .then(res => {
            window.localStorage.setItem('token', res.data.token)
            return null
        })
        .catch(err => err)

export const getMe = () =>
    instance.get("/users/me")

export const signup = (email: string, password: string) =>
    instance.post("/users/signup", { email, password })
        .then(res => window.localStorage.setItem('token', res.data.token))
        .catch(err => {
            if (err?.response?.data?.status?.value === 400) {
                return signin(email, password)
            }
        })

export const getMessages = () =>
    instance.get("/chat/list")
