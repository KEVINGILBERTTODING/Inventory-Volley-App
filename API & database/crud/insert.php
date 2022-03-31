<?php

include "koneksi.php";

$id = isset($_POST['id']) ? $_POST['id'] : '';
$kdbrg = isset($_POST['kdbrg']) ? $_POST['kdbrg'] : '';
$nmbrg = isset($_POST['nmbrg']) ? $_POST['nmbrg'] : '';
$hrgbeli = isset($_POST['hrgbeli']) ? $_POST['hrgbeli'] : '';
$hrgjual = isset($_POST['hrgjual']) ? $_POST['hrgjual'] : '';
$stok = isset($_POST['stok']) ? $_POST['stok'] : '';


if (empty($nmbrg) || empty($hrgjual) || empty($hrgbeli) || empty($hrgjual) || empty($stok) || empty($kdbrg)) {
	echo "Kolom isian tidak boleh kosong";
} else if (empty($id)) {
	$query = mysqli_query($con, "INSERT INTO barang (id, kdbrg, nmbrg, hrgbeli, hrgjual, stok) VALUES(0,'" . $kdbrg . "','" . $nmbrg . "','" . $hrgbeli . "','" . $hrgjual . "','" . $stok . "')");

	if ($query) {
		echo "Data berhasil di simpan";
	} else {
		echo "Error simpan Data";
	}
} else {
	$query = mysqli_query($con, "UPDATE barang set kdbrg = '" . $kdbrg . "', nmbrg = '" . $nmbrg . "', hrgbeli = '" . $hrgbeli . "', hrgjual = '" . $hrgjual . "', stok = '" . $stok . "' where id = '" . $id . "'");

	if ($query) {
		echo "Data berhasil di ubah";
	} else {
		echo "Error ubah Data";
	}
}
