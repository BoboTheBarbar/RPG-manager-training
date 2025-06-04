import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '../public/vite.svg'
import { TextField, Button, Container, Typography, Box } from '@mui/material'
import './App.css'

interface Item {
    id?: number;
    name: string;
    description: string;
    price: number;
}

function App() {
    return (
        <Container maxWidth="sm">
            <Box sx={{ textAlign: 'center', my: 4 }}>
                <div>
                    <a href="https://vite.dev" target="_blank">
                        <img src={viteLogo} className="logo" alt="Vite logo"/>
                    </a>
                    <a href="https://react.dev" target="_blank">
                        <img src={reactLogo} className="logo react" alt="React logo"/>
                    </a>
                </div>
                <Typography variant="h4" component="h1" gutterBottom>
                    Vendor Training
                </Typography>
                <ItemForm />
            </Box>
        </Container>
    )
}

function ItemForm() {
    const [formData, setFormData] = useState<Item>({
        name: '',
        description: '',
        price: 0
    });
    const [items, setItems] = useState<Item[]>([]);

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api/items', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                const newItem = await response.json();
                setItems([...items, newItem]);
                setFormData({
                    name: '',
                    description: '',
                    price: 0
                });
            } else {
                console.error('Failed to create item');
            }
        } catch (error) {
            console.error('Error creating item:', error);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === 'price' ? parseFloat(value) || 0 : value
        }));
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <TextField
                    required
                    name="name"
                    label="Item Name"
                    variant="outlined"
                    value={formData.name}
                    onChange={handleChange}
                />
                <TextField
                    required
                    name="description"
                    label="Description"
                    variant="outlined"
                    multiline
                    rows={3}
                    value={formData.description}
                    onChange={handleChange}
                />
                <TextField
                    required
                    name="price"
                    label="Price"
                    variant="outlined"
                    type="number"
                    inputProps={{ step: "0.01" }}
                    value={formData.price}
                    onChange={handleChange}
                />
                <Button 
                    type="submit" 
                    variant="contained" 
                    color="primary"
                    sx={{ mt: 2 }}
                >
                    Add Item
                </Button>
            </Box>

            <Box sx={{ mt: 4 }}>
                <Typography variant="h6" gutterBottom>
                    Items List
                </Typography>
                {items.map((item, index) => (
                    <Box key={item.id || index} sx={{ mb: 2, p: 2, border: '1px solid #ddd', borderRadius: 1 }}>
                        <Typography variant="subtitle1">
                            {item.name}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {item.description}
                        </Typography>
                        <Typography variant="body2" color="primary">
                            Price: ${item.price.toFixed(2)}
                        </Typography>
                    </Box>
                ))}
            </Box>
        </Box>
    );
}

export default App
