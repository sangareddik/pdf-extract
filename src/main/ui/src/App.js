import './App.css'
import React, { useState } from 'react'
import Header from './Header'
import AgGrid from './AgGrid'
import { errorToast } from './toast'
import Footer from './Footer'
import { Button } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Form from 'react-bootstrap/Form'
import Axios from 'axios'
import Spinner from './Spinner'

function App() {
  const [file, setFile] = useState(null)
  const [rowData, setRowData] = useState([])
  const [showSpinner, setShowSpinner] = useState([])

  const handleFileChange = e => {
    setFile(e.target.files[0])
  }

  const handleUpload = () => {
    setShowSpinner(true)
    const formData = new FormData()
    formData.append('file', file)

    Axios.post('http://localhost:8080/uploadFile', formData).then(
      response => {
        setShowSpinner(false)
        /* eslint-disable no-console */
        console.log(response.data)
        if (response.data.statusCode === undefined && response.data.statusCode !== null) setRowData(response.data)
        else {
          setRowData([])
          errorToast(response.data.successOrInfoMessage)
        }
      },
      () => errorToast('Exception Occured.')
    )
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
        <Button
          type='button'
          style={{ backgroundColor: '#006392', color: '#FFF' }}
          size='md'
          variant='outline-dark'
          onClick={handleUpload}
        >
          Upload
        </Button>

        <Button
          type='button'
          size='md'
          style={{ backgroundColor: '#006392', color: '#FFF' }}
          variant='outline-dark'
          onClick={handleExport}
        >
          Export
        </Button>
        <Button
          type='button'
          size='md'
          style={{ backgroundColor: '#006392', color: '#FFF' }}
          variant='outline-dark'
          onClick={handleReset}
        >
          Reset
        </Button>
      </div>
      <Spinner showSpinner={showSpinner} />
      <div className='m-4'>
        <AgGrid ref={agGridRef} rowData={rowData} />
      </div>
      <Footer />
    </div>
  )
}

export default App
