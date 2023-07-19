package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.BookingRepository;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/booking")
@RequiredArgsConstructor
@Controller
public class BookingController {

    private final TourPackagesRepository tourPackagesRepository;
    private final ItemRepository itemRepository;
    private final CarRepository carRepository;


    @GetMapping
    public String showBookingForm(ModelMap modelMap) {
        List<Car> cars = carRepository.findAll();
        List<Item> items = itemRepository.findAll();
        List<TourPackage> tours = tourPackagesRepository.findAll();
        modelMap.addAttribute("tours", tours);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        modelMap.addAttribute("booking", new Book());
        return "booking";
    }

    @PostMapping
    public String booking(@RequestParam("tourId") Integer tourId,
                          @RequestParam("groupSize") int groupSize,
                          @RequestParam("startDate") Date startDate,
                          @RequestParam("item") Item item,
                          @RequestParam("notes") String notes,
                          @AuthenticationPrincipal CurrentUser currentUser
                         ) {

        Optional<TourPackage> tour = tourPackagesRepository.findById(tourId);
        Book book = new Book();
        if (tourId == null) {
            book.setGuestCount(groupSize);
            book.setItem(item);
            book.setStartDate(startDate);
            book.setNotes(notes);
            book.setUser(currentUser.getUser());
        } else {
            book.setTourPackage(tour.get());
            book.setGuestCount(tour.get().getGroupSize());
            book.setStartDate(tour.get().getStartDate());
            book.setItem(tour.get().getItem());
            book.setCar(tour.get().getCar());
            book.setUser(currentUser.getUser());
        }
        return "tour";
    }

}
