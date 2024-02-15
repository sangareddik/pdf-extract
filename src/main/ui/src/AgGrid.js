import { AgGridReact } from 'ag-grid-react'
import 'ag-grid-community/styles/ag-grid.css'
import 'ag-grid-community/styles/ag-theme-balham.css'
import React, { useCallback, useRef } from 'react'

const AgGrid = React.forwardRef((props, ref, rowData) => {
  const gridRef = useRef()

  const columnDefs = [
    { field: 'cusip' },
    { field: 'accountNo', maxWidth: 120 },
    { field: 'settleMent' },
    { field: 'partNo', maxWidth: 120 },
    { field: 'securityDesc' },
    { field: 'quantity' },
    { field: 'amount' },
    { field: 'idControl' },
    { field: 'tagNo' }
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
