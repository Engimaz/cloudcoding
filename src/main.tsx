import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import './hooks/useWorker.ts'

import store from '@/store/index.ts';
import { persistor } from '@/store/index.ts'
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { PrimeReactProvider } from "primereact/api";
import "primereact/resources/themes/lara-light-cyan/theme.css";
import { ProgressSpinner } from 'primereact/progressspinner'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <Provider store={store} >
      <PersistGate loading={
        <div className="flex h-screen justify-center item-center">
          <ProgressSpinner />
        </div>
      } persistor={persistor}>
        <PrimeReactProvider value={{ unstyled: false }}>
          <App />
        </PrimeReactProvider>
      </PersistGate>
    </Provider>
  </React.StrictMode>
)
