# Vendor Management Frontend

This is the frontend application for the Vendor Management System, built with React, TypeScript, and Material-UI.

## Prerequisites

Before you begin, ensure you have the following installed:
- Node.js (v14 or higher)
- npm (usually comes with Node.js)
- A running instance of the backend server (default: http://localhost:8080)

## Setup

1. Clone the repository (if you haven't already):
   ```bash
   git clone <repository-url>
   cd vendor_app
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

## Development

To start the development server:

```bash
npm run dev
```

This will start the development server at http://localhost:5173 (default Vite port).

## Building for Production

To create a production build:

```bash
npm run build
```

The built files will be in the `dist` directory.

## Features

- Create new items with name, description, and price
- View list of created items
- Material-UI components for a modern UI experience
- TypeScript for better type safety
- Integration with backend REST API

## Project Structure

```
vendor_app/
├── src/                    # Source files
│   ├── assets/            # Static assets
│   ├── App.tsx            # Main application component
│   ├── main.tsx          # Application entry point
│   └── App.css           # Main stylesheet
├── public/                # Public static files
├── index.html            # HTML entry point
└── package.json          # Project dependencies and scripts
```

## Available Scripts

- `npm run dev`: Start development server
- `npm run build`: Build for production
- `npm run lint`: Run ESLint
- `npm run preview`: Preview production build locally

## Backend Integration

The application is configured to communicate with a backend server running at `http://localhost:8080`. If your backend is running on a different URL, you'll need to update the API endpoint in `src/App.tsx`.

## Contributing

1. Create a feature branch
2. Make your changes
3. Submit a pull request

## License

[Add your license information here]
