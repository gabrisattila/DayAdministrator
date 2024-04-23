package Classes.OwnFileTypes;

/*
A SAJÁT ROW helyzete:
nincs és nem is lesz teljesen saját.
Megoldás:
for(i=...; i<row.getLastCellNum(); i++)
  if(isNull(row.getCell(i)))
    row.createCell(i)
  ...
*/