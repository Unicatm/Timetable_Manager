//package com.example.androidproject.clase;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.androidproject.PaginaMaterii;
//import com.example.androidproject.PaginaTasks;
//import com.example.androidproject.R;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//
//public class MenuExtender{
//
//    public static void setupBottomNavigation(final Activity activity, BottomNavigationView bottomNavigationView) {
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Intent intent;
//
//                int id = item.getItemId();
//
//                if(id ==R.id.pgMaterii){
//                    intent = new Intent(activity, PaginaMaterii.class);
//                    activity.startActivity(intent);
//                    return true;
//                } else if (id == R.id.pgOrar) {
//                    return true;
//                } else if (id == R.id.pgAnunturi) {
//                    intent = new Intent(activity, PaginaTasks.class);
//                    activity.startActivity(intent);
//                    return true;
//                }else if (id == R.id.pgNotite) {
//                    return true;
//                }
//
//                return false;
//            }
//        });
//    }
//
//}
