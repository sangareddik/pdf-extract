import { AgGridReact } from 'ag-grid-react'
import 'ag-grid-community/styles/ag-grid.css'
import 'ag-grid-community/styles/ag-theme-balham.css'
import React, { useCallback, useRef } from 'react'

const AgGrid = React.forwardRef((props, ref) => {
  const gridRef = useRef()

  const rowData = [
    {
      CUSIP: 'M15342104',
      ACCOUNT: 'focus',
      SETTLEMENT: '8000',
      PART: 'ford',
      SECURITYDESCRIPTION: 'focus',
      QUANTITY: '8000',
      AMOUNT: 'ford',
      IDCONTROL: 'focus',
      TAG: '8000'
    },
    {
      CUSIP: 'ford',
      ACCOUNT: 'focus',
      SETTLEMENT: '8000',
      PART: 'ford',
      SECURITYDESCRIPTION: 'focus',
      QUANTITY: '8000',
      AMOUNT: 'ford',
      IDCONTROL: 'focus',
      TAG: '8000'
    },
    {
      CUSIP: 'ford',
      ACCOUNT: 'focus',
      SETTLEMENT: '8000',
      PART: 'ford',
      SECURITYDESCRIPTION: 'focus',
      QUANTITY: '8000',
      AMOUNT: 'ford',
      IDCONTROL: 'focus',
      TAG: '8000'
    }
  ]

  const columnDefs = [
    { field: 'CUSIP' },
    { field: 'ACCOUNT', maxWidth: 120 },
    { field: 'SETTLEMENT' },
    { field: 'PART', maxWidth: 120 },
    { field: 'SECURITYDESCRIPTION' },
    { field: 'QUANTITY' },
    { field: 'AMOUNT' },
    { field: 'IDCONTROL ' },
    { field: 'TAG' }
  ]

  const exportData = useCallback(() => {
    gridRef.current.api.exportDataAsCsv()
  }, [])

  React.useImperativeHandle(ref, () => ({
    exportData,
    resetData
  }))

  const resetData = useCallback(() => {
    gridRef.current.api.setRowData([])
  }, [])

  return (
    <div className='ag-theme-balham' style={{ height: 580, width: 1650 }}>
      <AgGridReact ref={gridRef} rowData={rowData} columnDefs={columnDefs} animateRows={true} />
    </div>
  )
})

export default AgGrid
