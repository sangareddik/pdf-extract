import React from 'react'
import { toast } from 'react-toastify'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {
  faCheckCircle,
  faInfoCircle,
  faExclamationTriangle,
  faExclamationCircle
} from '@fortawesome/free-solid-svg-icons'

const param = {
  position: toast.POSITION.TOP_RIGHT,
  closeButton: true,
  pauseOnHover: true,
  autoClose: 30000
}
const classNames = 'text-light'
const contentConfig = [
  {
    type: 'SUCCESS',
    iconInfo: faCheckCircle,
    classNames
  },
  {
    type: 'ERROR',
    iconInfo: faExclamationTriangle,
    classNames
  },
  {
    type: 'WARNING',
    iconInfo: faExclamationCircle,
    classNames: 'text-dark'
  },
  {
    type: 'INFO',
    iconInfo: faInfoCircle,
    classNames
  }
]
const ToastContent = ({ type, message }) => {
  const contentObj = findContentType(type)[0]

  function findContentType(type) {
    return contentConfig.filter(item => {
      return item.type === type
    })
  }

  return (
    <div className={contentObj.classNames}>
      <div className='d-flex'>
        <span className='pr-2 mr-2'>
          <FontAwesomeIcon size='sm' icon={contentObj.iconInfo}></FontAwesomeIcon>
        </span>
        <span className='message'>{message}</span>
      </div>
    </div>
  )
}

const MultiWarningToastContent = ({ type, messages }) => {
  const contentObj = findContentType(type)[0]

  function findContentType(type) {
    return contentConfig.filter(item => {
      return item.type === type
    })
  }

  return (
    <div className={contentObj.classNames}>
      {messages.map((message, id) => {
        return (
          <div className='d-flex' key={id} style={{ margin: '6px' }}>
            <span className='pr-2 mr-2'>
              <FontAwesomeIcon size='sm' icon={contentObj.iconInfo}></FontAwesomeIcon>
            </span>
            <span key={id} className='message'>
              {message}
            </span>
          </div>
        )
      })}
    </div>
  )
}

const successToast = message => {
  toast(<ToastContent type={'SUCCESS'} message={message} />, {
    className: 'bg-success',
    ...param
  })
}

const errorToast = message => {
  toast(<ToastContent type={'ERROR'} message={message} />, {
    className: 'bg-danger',
    ...param
  })
}

const warningToast = message => {
  toast(<ToastContent type={'WARNING'} message={message} />, {
    className: 'bg-warning',
    ...param
  })
}

const infoToast = message => {
  toast(<ToastContent type={'INFO'} message={message} />, {
    className: 'bg-primary',
    ...param
  })
}

const multipleWarningToast = messages => {
  toast(<MultiWarningToastContent type={'WARNING'} messages={messages} />, {
    className: 'bg-warning',
    ...param
  })
}

const multipleErrorToast = messages => {
  toast(<MultiWarningToastContent type={'ERROR'} messages={messages} />, {
    className: 'bg-danger',
    ...param
  })
}
export { successToast, errorToast, warningToast, infoToast, multipleWarningToast, multipleErrorToast }
