import React, {useEffect, useState} from 'react';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import FormControlLabel from '@mui/material/FormControlLabel';
import Checkbox from '@mui/material/Checkbox';
import { Link, redirect, useNavigate } from 'react-router-dom';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {getMe, showAlert, signup} from "./utils";
import WebSock from "./WebSocket";
const defaultTheme = createTheme();
function App() {
    const [auth, setAuth] = useState(!!window.localStorage.getItem('token'))

    if (!auth) return <AuthForm setAuth={setAuth}/>
    return (
        <div>
            <WebSock />
        </div>
    );
}

export default App;


function AuthForm({setAuth}: {setAuth: any}) {

    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const onSubmit = () => {
        signup(email, password)
            .then((err) => {
                if (err) {
                    showAlert(err.response?.data?.message || 'Ошибка')
                } else {
                    setAuth(true)
                }
            })
    }
    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign in
                </Typography>
                <Box component="form" onSubmit={e => {
                    e.preventDefault()
                    onSubmit()
                }} noValidate sx={{ mt: 1 }}>
                    <TextField
                        margin="normal"
                        value={email}
                        onChange={e => setEmail(e.target.value)}
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                    />
                    <TextField
                        margin="normal"
                        required
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        fullWidth
                        name="password"
                        label="Password"
                        type="password"
                        id="password"
                        autoComplete="current-password"
                    />
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Sign In
                    </Button>
                </Box>
            </Box>
        </Container>
    )
}