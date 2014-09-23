<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alta documentos-Flux IT</title>
</head>
<body>
	<form action="altaDocumento">
		<table bgcolor="#00CCA3" align="center" width="80%">
			<tr>
				<td><p style="padding-left: 10px;">
						Adjunta el documento seleccionado<b style="color: red">*</b> <input
							type="file" tabindex=""
							title="Seleccione un archivo - Obligatorio" value="" /><br>
					</p></td>
			</tr>
			<tr>
				<td>
					<p style="padding-left: 10px;">
						Seleccione el tipo de documento<b style="color: red">*</b> <select>
							<option value="" selected="selected">Seleccionar</option>
							<option value="1">Público</option>
							<option value="2">Privado</option>
							<option value="3">Draft</option>
						</select>
				</td>
			</tr>
			<tr>
				<td>
				</td>
			</tr>
			<tr >
				<td align="center" style="padding-left: 10px;"><textarea rows="5" cols="50" id="">See	</textarea></td>
			</tr>
		</table>
	</form>
	<input  type="button" align="middle">
</body>
</html>