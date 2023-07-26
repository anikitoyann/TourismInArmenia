package com.example.tourarmeniaweb.controller;

import com.example.tourarmeniacommon.entity.Book;
import com.example.tourarmeniacommon.entity.Car;
import com.example.tourarmeniacommon.entity.Item;
import com.example.tourarmeniacommon.entity.TourPackage;
import com.example.tourarmeniacommon.repository.BookingRepository;
import com.example.tourarmeniacommon.repository.CarRepository;
import com.example.tourarmeniacommon.repository.ItemRepository;
import com.example.tourarmeniacommon.repository.TourPackagesRepository;
import com.example.tourarmeniacommon.service.ItemService;
import com.example.tourarmeniaweb.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/booking")
@RequiredArgsConstructor
@Controller
@Slf4j
public class BookingController {

    private final TourPackagesRepository tourPackagesRepository;
    private final ItemService itemService;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;


    @GetMapping("/createCustomTour")
    public String showBookingForm(ModelMap modelMap) {
        log.info("Showing the booking form");
        List<Car> cars = carRepository.findAll();
        List<Item> items = itemService.findAll();
        List<TourPackage> tours = tourPackagesRepository.findAll();
        modelMap.addAttribute("tours", tours);
        modelMap.addAttribute("cars", cars);
        modelMap.addAttribute("items", items);
        modelMap.addAttribute("booking", new Book());
        return "booking";
    }

    @PostMapping("/createCustomTour")
    public String createBooking(@RequestParam("groupSize") int groupSize,
                                @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                @RequestParam("item.id") Integer itemId,
                                @RequestParam("car.id") Integer carId,
                                @RequestParam("notes") String notes,
                                @AuthenticationPrincipal CurrentUser currentUser
    ) {
        log.info("Creating a custom tour booking");
        Item item = itemService.findById(itemId).get();
        Car car = carRepository.findById(carId).get();
        Book book = new Book();
        book.setGuestCount(groupSize);
        book.setItem(item);
        book.setCar(car);
        book.setStartDate(startDate);
        book.setNotes(notes);
        book.setUser(currentUser.getUser());
        bookingRepository.save(book);
        return "redirect:/tour";
    }
@PostMapping
public String bookTour(@AuthenticationPrincipal CurrentUser currentUser,
                       @RequestParam("tourId") Integer tourId,
                       RedirectAttributes redirectAttributes) {
    log.info("Booking a tour by tour ID");
    Optional<TourPackage> tour = tourPackagesRepository.findById(tourId);
    Book book = new Book();
    book.setTourPackage(tour.get());
    book.setGuestCount(tour.get().getGroupSize());
    book.setStartDate(tour.get().getStartDate());
    book.setItem(tour.get().getItem());
    book.setCar(tour.get().getCar());
    book.setUser(currentUser.getUser());
    bookingRepository.save(book);
    return "redirect:/tour";
}

    @GetMapping("/booking")
    public String bookTour(@RequestParam("tourId") Integer tourId,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        log.info("Redirecting to the tour");
        return "redirect:/tour";

}}

