import './App.css'
import React, { useState } from 'react'
import Header from './Header'
import AgGrid from './AgGrid'
import { errorToast } from './toast'
import Footer from './Footer'
import { Button, Modal } from 'react-bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import Form from 'react-bootstrap/Form'
import Axios from 'axios'
import Spinner from './Spinner'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faExclamationTriangle, faFileExport, faTimes, faUpload } from '@fortawesome/free-solid-svg-icons'
import './style.scss'

function App() {
  const [file, setFile] = useState(null)
  const [rowData, setRowData] = useState([])
  const [showSpinner, setShowSpinner] = useState([])
  const [showModal, setShowModal] = useState(false)
  const [content, setContent] = useState('')

  const closeModal = () => {
    setShowModal(false)
  }

  const handleFileChange = e => {
    setFile(e.target.files[0])
  }

  const handleUpload = () => {
    handleReset()
    setShowSpinner(true)
    const formData = new FormData()
    if (file === null) {
      setContent('No File is uploaded')
      setShowModal(true)
      return
    }
    formData.append('file', file)
    Axios.post('./uploadFile', formData).then(
      response => {
        setShowSpinner(false)
        /* eslint-disable no-console */
        console.log(response.data)
        if (response.data.statusCode === undefined && response.data.statusCode !== null) setRowData(response.data)
        else {
          setContent(response.data.successOrInfoMessage)
          setShowModal(true)
          setRowData([])
          errorToast(response.data.successOrInfoMessage)
        }
      },
      () => errorToast('Exception Occured.')
    )
  }
  const handleExport = () => {
    if (rowData.length === 0) {
      setContent('No Rows To Export')
      setShowModal(true)
      return
    }
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
          style={{ backgroundColor: '#006392', color: '#FFF', width: '120px' }}
          size='md'
          variant='outline-dark'
          onClick={handleUpload}
        >
          <FontAwesomeIcon style={{ float: 'left' }} icon={faUpload} />
          Upload
        </Button>

        <Button
          type='button'
          size='md'
          style={{ backgroundColor: '#006392', color: '#FFF', width: '120px' }}
          variant='outline-dark'
          onClick={handleExport}
        >
          <FontAwesomeIcon style={{ float: 'left' }} icon={faFileExport} />
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
      {showModal && (
        <Modal centered backdrop='static' show={showModal} onHide={closeModal}>
          <Modal.Header style={{ backgroundColor: '#006392', height: '45px', color: 'white' }}>
            <FontAwesomeIcon style={{ color: 'red' }} icon={faExclamationTriangle} />
            <Modal.Title>Error</Modal.Title>
            <div className='mbse-modal__header_close' data-dismiss='modal'>
              <span className='close-division'>
                <FontAwesomeIcon className='modal-close-btn' icon={faTimes} onClick={closeModal} />
              </span>
            </div>
          </Modal.Header>
          <Modal.Body>{content}</Modal.Body>
          <Modal.Footer>
            <Button variant='outline-dark' onClick={closeModal}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      )}
      <Footer />
    </div>
  )
}

export default App
