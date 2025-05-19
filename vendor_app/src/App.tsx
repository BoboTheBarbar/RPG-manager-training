import {useState} from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '../public/vite.svg'
import {TextField, Button} from '@mui/material'
import './App.css'

function App() {
    return (
        <>
            <div>
                <a href="https://vite.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo"/>
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="logo react" alt="React logo"/>
                </a>
            </div>
            <h1>Vendor Training</h1>

            <MyFormComponent>
            </MyFormComponent>
        </>
    )
}

function MyFormComponent() {
    const [inputValue, setInputValue] = useState("");
    const [items, setItems] = useState<string[]>([]);

    const handleSubmit = (event:any) => {
        event.preventDefault(); // verhindert Seitenreload
        console.log("Form submitted with:", inputValue);

        setItems([...items, inputValue])
        setInputValue("")
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <TextField
                    id="standard-basic"
                    label="Standard"
                    variant="standard"
                    value={inputValue}
                    onChange={(e) => setInputValue(e.target.value)}
                />
                <Button type="submit">Absenden</Button>
            </form>
            <ul>
                {items.map((item, index) => (
                    <li key={index}>{item}</li>
                ))}
            </ul>
        </>
    );
}

export default App
