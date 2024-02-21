import { AgGridReact } from 'ag-grid-react'
import 'ag-grid-community/styles/ag-grid.css'
import 'ag-grid-community/styles/ag-theme-balham.css'
import React, { useCallback, useRef } from 'react'
import './style.scss'

const AgGrid = React.forwardRef((props, ref) => {
  const gridRef = useRef()

  const columnDefs = [
    { headerName: 'Cusip', field: 'cusip' },
    { headerName: 'Account #', field: 'accountNo', maxWidth: 120 },
    { headerName: 'Settlement', field: 'settleMent' },
    { headerName: 'Part #', field: 'partNo', maxWidth: 120 },
    { headerName: 'Security Desc', field: 'securityDesc' },
    { headerName: 'Quantity', field: 'quantity' },
    { headerName: 'Amount', field: 'amount' },
    { headerName: 'Id Control', field: 'idControl' },
    { headerName: 'Tag #', field: 'tagNo', maxWidth: 190 }
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
    <div className='ag-theme-balham' style={{ height: 567, width: 1650 }}>
      <AgGridReact ref={gridRef} rowData={props.rowData} columnDefs={columnDefs} animateRows={true} />
    </div>
  )
})

export default AgGrid
