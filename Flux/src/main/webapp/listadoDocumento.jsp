<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listado de Documento - Flux IT</title>
</head>
<body bgcolor="#00CCA3">
	<br>
	<html:form action="/listado.do"  >
		
		<table align="center" border="1" bordercolor="black" cellpadding="6"
			cellspacing="0">
			<thead>
				<tr>
					<td>Nombre del documento</td>
					<td>Fecha de subida</td>
					<td>Tipo de documento</td>
					<td>Owner del documento</td>
					<td>Documento (Descargable)</td>
				</tr>
			</thead>
			<tbody align="center">
				<%%>
			</tbody>

		</table>
		<input type="submit" onclick="retornar();" value='Volver' />
		<input type="hidden" name="reqCode" value="">
	</html:form>
</body>
</html>
<script type="text/javascript">
	function retornar() {
		listadoForm.reqCode.value = 'volver';
		listadoForm.submit();
	}
</script>