import React from 'react'
import { faWheelchair, faKey, faFileContract } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'

const Footer = () => {
  return (
    <footer
      className='bg-primary text-light p-0 m-0 mt-2 d-flex justify-content-center align-items-baseline'
      style={{ fontSize: '0.8rem' }}
    >
      <p className='my-0'>
        {'\u00A9'}&nbsp;{new Date().getFullYear()} Broadridge Financial Solutions, Inc.{' '}
      </p>
      &nbsp; &nbsp;
      <a href='https://www.broadridge.com/legal/accessibility' target='_blank' rel='noreferrer'>
        <FontAwesomeIcon icon={faWheelchair} className='fa-sm text-light' />
      </a>
      &nbsp; &nbsp;
      <a href='http://www.broadridge.com/privacy.asp' target='_blank' rel='noreferrer'>
        <FontAwesomeIcon icon={faKey} className='fa-sm text-light' />
      </a>
      &nbsp; &nbsp;
      <a href='http://www.broadridge.com/terms.asp' target='_blank' rel='noreferrer'>
        <FontAwesomeIcon icon={faFileContract} className='fa-sm text-light' />
      </a>
    </footer>
  )
}
// Note we might need to display Application name , if noy will remove this HOC
export default Footer
