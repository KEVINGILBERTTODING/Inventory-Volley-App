<?php
include "koneksi.php";

$id = isset($_POST['id']) ? $_POST['id'] : '';
class emp
{
}

if (empty($id)) {
	echo "Error Mengambil Data id kosong";
} else {
	$query 	= mysqli_query($con, "SELECT * FROM barang WHERE id='" . $id . "'");
	$row 	= mysqli_fetch_array($query);

	if (!empty($row)) {
		$response = new emp();
		$response->id = $row["id"];
		$response->kdbrg = $row["kdbrg"];
		$response->nmbrg = $row["nmbrg"];
		$response->hrgbeli = $row["hrgbeli"];
		$response->hrgjual = $row["hrgjual"];
		$response->stok = $row["stok"];

		echo (json_encode($response));
	} else {

		echo ("Error Mengambil Data");
	}
}
