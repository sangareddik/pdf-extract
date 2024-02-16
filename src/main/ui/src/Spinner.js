import React from 'react'
import _ from 'lodash'
import axios from 'axios'
//import { ServiceConfig } from '../config/ServerConfig'
import './Spinner.css'
//import { ApplicationProperties } from '../config/ApplicationProperties'
import { errorToast } from './toast'
import { endPointsWhiteList, geoApi } from './SpinnerEndPointsWhitelist'

class Spinner extends React.Component {
  constructor(props) {
    super(props)
    this.state = { showSpinner: false }
    this.spinnerArray = []
  }

  componentDidMount() {
    axios.interceptors.request.use(
      config => {
        if (endPointsWhiteList.indexOf(config.url) === -1 && config.url.indexOf(geoApi) !== 0) {
          this.spinnerArray.push(config.url)
          this.setState({ showSpinner: true })
        }

        // adding 'X-Requested-With' header for every req to distinguish the normal web req and xhr req
        // to handle the book marked url click.
        config.headers['X-Requested-With'] = 'XMLHttpRequest'
        config.headers['Access-Control-Allow-Origin'] = '*'
        return { ...config, withCredentials: false }
      },
      error => {
        this.setState({ showSpinner: false })
        errorToast('Axios request error interceptor')
        return Promise.reject(error)
      }
    )

    axios.interceptors.response.use(
      response => {
        const index = this.spinnerArray.indexOf(response.config.url)
        if (index > -1) {
          this.spinnerArray.splice(index, 1)
        }
        if (this.spinnerArray.length === 0) this.setState({ showSpinner: false })
        return response
      },
      error => {
        if (error && error.response) {
          const index = this.spinnerArray.indexOf(error.response.config.url)
          if (index > -1) {
            this.spinnerArray.splice(index, 1)
          }
        }
        if (this.spinnerArray.length === 0) this.setState({ showSpinner: false })
        if (axios.isCancel(error)) {
          return Promise.reject(error)
        } else {
          const status = error.response.status
          let messageText = ''

          if (status === 401) {
            errorToast(error.response.errorCode)
          } else if (status === 403) {
            messageText = error.message

            if (
              messageText === null ||
              messageText === undefined ||
              messageText === '' ||
              messageText !== 'Access denied!'
            )
              messageText = 'Your session has expired. Please logout and login again'

            errorToast(messageText)
          } else if (status === 440) {
            messageText = 'Your session has expired. Please logout and login again'
            errorToast(messageText)
          } else if (status === 400) {
            const msg = _.get(error.response, 'data.errors[0].message')
            if (msg && msg.trim().length > 0) {
              errorToast(msg)
            } else {
              errorToast(
                `User request encountered a problem. 
              Please retry, if problem persists please logout and login again`
              )
            }
          } else {
            messageText = `User request encountered a problem. 
            Please retry, if problem persists please logout and login again`
            errorToast(messageText)
          }

          this.setState({ showSpinner: false })

          return Promise.reject(error)
        }
      }
    )

    // axios.get(`${ServiceConfig.staticDataServiceURL}/uiswitches/all`).then(
    //   response => {
    //     ApplicationProperties.loadProperties(response.data)
    //   },
    //   () => {}
    // )
  }

  render() {
    return (
      this.state.showSpinner && (
        <div id='overlay'>
          <div className='spinner-border text-primary' role='status'>
            <span className='sr-only'>Loading...</span>
          </div>
        </div>
      )
    )
  }
}

export default Spinner
