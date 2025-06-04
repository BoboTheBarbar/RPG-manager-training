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

3. Generate API client:
   ```bash
   npm run generate-api
   ```
   This will generate type-safe API client code from the OpenAPI specification located in the backend project.

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
- Type-safe API client generated from OpenAPI specification
- Automatic validation of API requests and responses

## Project Structure

```
vendor_app/
├── src/                    # Source files
│   ├── assets/            # Static assets
│   ├── api/               # Generated API client code
│   │   ├── apis/         # API endpoint implementations
│   │   ├── models/       # TypeScript interfaces for API models
│   │   └── runtime.ts    # API client runtime
│   ├── App.tsx           # Main application component
│   ├── main.tsx         # Application entry point
│   └── App.css          # Main stylesheet
├── public/               # Public static files
├── index.html           # HTML entry point
└── package.json         # Project dependencies and scripts
```

## Available Scripts

- `npm run dev`: Start development server
- `npm run build`: Build for production
- `npm run lint`: Run ESLint
- `npm run preview`: Preview production build locally
- `npm run generate-api`: Generate API client from OpenAPI specification

## API Integration

The application uses a type-safe API client generated from the OpenAPI specification. The client is automatically generated using the `@openapitools/openapi-generator-cli` package.

### API Client Generation

The API client is generated from the OpenAPI specification located at `../VendorBackend/src/main/resources/api-spec.yaml`. To regenerate the client after changes to the API specification:

```bash
npm run generate-api
```

### API Client Usage

The generated API client provides type-safe methods for all API endpoints:

```typescript
import { DefaultApi, Configuration } from './api';
import type { Item, ItemCreate } from './api';

// Initialize API client
const api = new DefaultApi(new Configuration({
    basePath: 'http://localhost:8080'
}));

// Get all items
const items = await api.getAllItems();

// Create a new item
const newItem = await api.createItem({
    itemCreate: {
        name: 'Example Item',
        description: 'This is an example item',
        price: 9.99
    }
});
```

### API Models

The generated API client includes TypeScript interfaces for all API models:

- `Item`: Represents an item in the system
- `ItemCreate`: Data required to create a new item
- `Error`: Error response from the API

## Backend Integration

The application is configured to communicate with a backend server running at `http://localhost:8080`. If your backend is running on a different URL, you'll need to update the `basePath` in the API client configuration in `src/App.tsx`.

## Contributing

1. Create a feature branch
2. Make your changes
3. Update the OpenAPI specification if making API changes
4. Regenerate the API client if needed
5. Submit a pull request

## Troubleshooting

### API Client Generation Issues

If you encounter issues with API client generation:

1. Ensure the OpenAPI specification is valid
2. Check that the backend server is running
3. Verify the path to the OpenAPI specification file is correct
4. Try removing the `src/api` directory and regenerating

### CORS Issues

If you encounter CORS issues:

1. Verify the backend server is running
2. Check that the backend allows requests from http://localhost:5173
3. Ensure you're using the correct backend URL in the API client configuration

## License

[Add your license information here]
