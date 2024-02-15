import './App.css'
import React, { useState } from 'react'
import Header from './Header'
import AgGrid from './AgGrid'
import { Button } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Form from 'react-bootstrap/Form'

function App() {
  const [file, setFile] = useState(null)

  const handleFileChange = e => {
    setFile(e.target.files[0])
  }

  const handleUpload = () => {
    const formData = new FormData()
    formData.append('file', file)

    fetch('http://example.com/upload', {
      method: 'POST',
      body: formData
    })
      .then(response => {
        /* eslint-disable no-console */
        console.log(response)
        /* eslint-enable no-console */
      })
      .catch(error => {
        /* eslint-disable no-console */
        console.log(error)
        /* eslint-enable no-console */
      })
  }
  const handleExport = () => {
    agGridRef.current.exportData()
  }

  const handleReset = () => {
    agGridRef.current.resetData()
  }

  const agGridRef = React.useRef()
  return (
    <div>
      <Header />
      <div className='m-4 d-flex justify-content-between' style={{ width: 900 }}>
        <Form.Control type='file' onChange={handleFileChange} style={{ width: 500 }} />
        <Button type='button' size='md' variant='primary' onClick={handleUpload}>
          Upload
        </Button>

        <Button type='button' size='md' variant='primary' onClick={handleExport}>
          Export
        </Button>
        <Button type='button' size='md' variant='outline-dark' onClick={handleReset}>
          Reset
        </Button>
      </div>
      <div className='m-4'>
        <AgGrid ref={agGridRef} />
      </div>
    </div>
  )
}

export default App
