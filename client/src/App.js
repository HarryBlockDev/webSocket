import React, { useEffect, useState } from 'react';

import Stomp from 'stomp-websockets'
import SockJS from 'sockjs-client';
import webstomp from 'webstomp-client';

const SOCKET_URL = 'http://localhost:8082/hello';

const App = () => {
  const [message, setMessage] = useState('You server message here.');
  const [client, setClient] = useState(null);

  let sock = new SockJS(SOCKET_URL);
  let clients = webstomp.over(sock, {debug : false});

  useEffect(() => {
    console.log(clients);
    clients.connect({login: 'mylogin',
    passcode: 'mypasscode',
    // additional header
    'client-id': 'my-client-id'}, () => {

      clients.subscribe('/users/queue/messages', function(message) {
        console.log(message);
        setMessage(message.body);
      });
    });
  }, [clients])


  return (
    <div>
      <div>{message}</div>
      <button onClick={() => {
        clients.send("/app/hello", "users", {username: "kji"});
      }}>send</button>
    </div>
  );
}

export default App;
