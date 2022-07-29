import styles from "../styles/Home.module.css";
import Head from "next/head";
import {useEffect, useRef, useState} from "react";
import SockJS from "sockjs-client"

export default function index() {
    const [socketInstance, setSocketInstance] = useState();
    const [data, setData] = useState([]);
    const inputRef = useRef()
    useEffect(() => {
        if (!socketInstance && typeof window !== "undefined") {
            const webSocket = new SockJS("http://localhost:9090/chat");
            console.log(webSocket.url)
            webSocket.onmessage = function (msg) {
              console.log(msg.data)
                const response = JSON.parse(msg.data);
                if(response.msgType==="generation"){
                    setData(response.data)
                }
                if(response.msgType==="auto"){
                    setData(response.data)
                }
            }
            webSocket.onclose = function () {
                alert("Server Disconnect You");
            }
            webSocket.onopen = function () {
                console.log("open")
                webSocket.send(JSON.stringify({data:null,type:"connection"}))
            }
            setSocketInstance(webSocket)
        }
    }, [socketInstance])
    const send = (type) => {
        const data = inputRef.current.value
        socketInstance.send(JSON.stringify({data, type}))

    }
    return (
        <div className="container-fluid full-height">
            <div className="row chat-text">
            </div>
            <div className="row input-text border">
                <div className="col-md-10">
                    <input type="number" className="btn-block" id="msg" ref={inputRef}/>
                </div>
                <div className="col-md-2">
                    <button type="button" className="btn btn-primary btn-block btn-lg" id="auto"
                            onClick={() => send("auto")}>
                        AUTO
                    </button>
                    <button type="button" className="btn btn-primary btn-block btn-lg" id="generation"
                            onClick={() => send("generation")}>
                        GENERATION
                    </button>
                </div>
            </div>
            <div className="col-md-10 border chat-text" id="numbers">
                {data.map((element,index)=><div key={index }>{element+","}</div>)}
            </div>
        </div>
    )
}

