package com.example.uas202102266;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CuacaAdapter extends RecyclerView.Adapter<CuacaViewHolder> {
    private Activity activity;
    private List<CuacaListModel> listModelList;
    private CuacaRootModel namaVariabel_rootModel;

    public CuacaAdapter(Activity activity, CuacaRootModel namaVariabel_rootModel) {
        this.activity = activity;
        this.namaVariabel_rootModel = namaVariabel_rootModel;

        listModelList = namaVariabel_rootModel.getListModelList();
    }

    @NonNull
    @Override
    public CuacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater namaVariabel_layoutInflater = LayoutInflater.from(parent.getContext());
        View namaVariabel_view = namaVariabel_layoutInflater.inflate(R.layout.list_cuaca, parent, false);
        return new CuacaViewHolder(namaVariabel_view);
    }

    private double toCelcius(double kelvin) {
        return kelvin = 272.15;
    }

    public String formatNumber(double number, String format) {
        DecimalFormat namaVariabel_decimalFormat = new DecimalFormat(format);
        return namaVariabel_decimalFormat.format(number);
    }

    @Override
    public void onBindViewHolder(@NonNull CuacaViewHolder holder, int position) {
        CuacaListModel namaVariabel_listModel = listModelList.get(position);
        CuacaWeatherModel namaVariabel_weatherModel = namaVariabel_listModel.getWeatherModelList().get(0); // dibuat seperti ini karena berbentuk array dan knp get(0) karena data di weather nya dari link berisi 1 data.
        CuacaMainModel namaVariabel_mainModel = namaVariabel_listModel.getMainModel();

        String suhu = formatNumber(toCelcius(namaVariabel_mainModel.getTemp_min()), "###.##") + "°C - " +
                formatNumber(toCelcius(namaVariabel_mainModel.getTemp_max()), "###.##") + "°C";

        String iconUrl = "https://openweathermap.org/img/wn/" + namaVariabel_weatherModel.getIcon() + "@2x.png";
        Picasso.with(activity).load(iconUrl).into(holder.cuacaImageView);

        String tanggalWaktuWib = formatWib(namaVariabel_listModel.getDt_txt());

        holder.namaTextView.setText(namaVariabel_weatherModel.getMain());
        holder.deskripsiTextView.setText(namaVariabel_weatherModel.getDescription());
        holder.tglWaktuTextView.setText(tanggalWaktuWib);
        holder.suhuTextView.setText(suhu);
    }

    private String formatWib(String tanggalWaktuGmt_string) {
        Log.d("*tw*", "Waktu GMT : " + tanggalWaktuGmt_string);

        Date tanggalWaktuGmt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //harus sesuai dengan data yang ada di link atau json

        try {
            tanggalWaktuGmt = sdf.parse(tanggalWaktuGmt_string);
        } catch (ParseException e) {
            Log.d("*tw*", e.getMessage());
        }

        // Calender diisini berguna untuk dari data Gmt terus ditambah 7 jam dengan fungsinya di baris ke 125 yaitu HOUR_OF_DAY
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(tanggalWaktuGmt);
        calendar.add(Calendar.HOUR_OF_DAY,7);

        Date tanggalWaktuWib = calendar.getTime();

        String tanggalWaktuWib_string = sdf.format(tanggalWaktuWib);
        tanggalWaktuWib_string = tanggalWaktuWib_string.replace("00.00", "00 WIB"); //Berguna untuk menhapus detik dari data nya

        //Fungsi *tw* berguna untuk memfilter data di Logcat setelah di log.d, *tw* juga bisa di ganti dengan inisial sendiri
        Log.d("*tw*", "Tanggal Waktu Indonesia Barat : " + tanggalWaktuWib_string);
        return tanggalWaktuWib_string;
    }

    @Override
    public int getItemCount() {
        return (listModelList != null) ? listModelList.size() : 0;
    }
}
