import React, {RefObject, useEffect, useRef, useState} from 'react';
import {apiHost, apiPort, getMe, getMessages, showAlert} from "./utils";
import {ReactComponent as SendSvg} from "./send.svg";
import './style.css'
function WebSock() {
    const [me, setMe] = useState<User | null>(null)
    const chatContainerRef = useRef<HTMLDivElement>(null)
    const socket = useRef<WebSocket>()
    // const [connected, setConnected] = useState(false)
    const [messages, setMessages] = useState<Message[]>([])
    const [input, setInput] = useState('')


    useEffect(() => {
        if (!me) return
        const url = `ws://${apiHost}:${apiPort}/chat/client?token=${window.localStorage.getItem('token')}`
        try {
            socket.current = new WebSocket(url)
            socket.current.onopen = (event) => {
                // setConnected(true)
                sendMessage(`Пользователь ${me.email} подключился`, 'system')
            }
            socket.current.onmessage = (event) => {
                const message = JSON.parse(event.data)
                setMessages(prev => [...prev, message])
            }
            socket.current.onclose = () => {
                // showAlert('Вы отключились', 'alert-success')
            }
            socket.current.onerror = (err) => {
                // showAlert(JSON.stringify(err), 'alert-danger')
            }
        } catch (e) {
            console.log(e)
        }
    }, [me]);

    const sendMessage = (text: String, type: 'user' | 'system' = 'user') => {
        if (!text.trim()) return
        const message = JSON.stringify({
            message: text,
            type,
        });
        socket.current!.send(message)
    }

    useEffect(() => {
        getMe()
            .then(res => {
                setMe(res.data)
            })
            .catch(err => {
                showAlert(err.response?.data?.message || 'Ошибка')
            })
    }, []);

    useEffect(() => {
        getMessages()
            .then(res => {
                console.log(res.data)
                setMessages(prev => res.data)
            })
    }, []);

    useEffect(() => {
        const container = chatContainerRef.current
        if (container) {
            container.scrollTop = container.scrollHeight
        }
    }, [messages]);

    return (
        <div>
            <div className='chat-window'>
                <header>
                    {me?.email}
                </header>
                <div className="chat-container" ref={chatContainerRef}>|
                    {messages.map(message =>
                        message.type === "system" ? <SystemMessage key={message.id} data={message}/>
                            : <UserMessage key={message.id} data={message} user={me!}/>
                    )}
                </div>
                <footer>
                    <input
                        value={input}
                        onChange={e => setInput(e.target.value)}
                        onKeyPress={key => {
                            if (key.code === 'Enter') {
                                sendMessage(input)
                                setInput('')
                            }
                        }}/>
                        <SendSvg
                            width='40px'
                            height='40px'
                            onClick={() => {
                                sendMessage(input)
                                setInput('')
                            }}
                            style={{cursor: 'pointer'}}
                        />
                </footer>
            </div>
    </div>
    );
}

export default WebSock;


interface Message {
    author: string
    id: number
    message: string
    type: string
}

interface User {
    id: number
    email: string
}


function SystemMessage({data}: {data: Message}) {
    return (
        <div className='message system-message'>
            {data.message}
        </div>
    )
}

function UserMessage({data, user}: {data: Message, user: User}) {
    return (
        <div>
            <div className={'message user-message ' + (data.author === user.email ? 'my-message' : '')}>
                {data.author !== user.email &&
                <p>{data.author}</p>
                }
                {data.message}
            </div>
        </div>
    )
}