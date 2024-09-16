package com.movie.prompt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieApiRunner implements ApplicationRunner {

    private final MovieApiService movieApiService;

    private final GptService gptService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n");
        Thread.sleep(2000);
        System.out.println("Hi Folk! Welcome to the wonderful Movie Navigator!!!");
        Thread.sleep(2000);
        System.out.println("Here you can find your favorite movie, and all information about!!!");
        Thread.sleep(1000);
        System.out.println("Enjoy!");
        Thread.sleep(1000);

        System.out.println("\n");
        System.out.println("Enter the movie name:");

        getMoviePrompt(scanner.nextLine());

        boolean prompted = true;

        while(prompted){
            Thread.sleep(1000);
            System.out.println("\n");
            System.out.println("Would you like to find more (yes/no)? ");

            boolean searchMovie = true;

            String firstAnswer = scanner.nextLine();

            if(firstAnswer.equalsIgnoreCase("yes")){

                while(searchMovie){

                    System.out.println("Enter the movie name:");
                    getMoviePrompt(scanner.nextLine());

                    Thread.sleep(1000);
                    System.out.println("\n");
                    System.out.println("Would you like to find more (yes/no)? ");

                    if(scanner.nextLine().equalsIgnoreCase("no")){
                        System.out.println("Hope you enjoyed it, we look forward to seeing you again=)");
                        searchMovie = false;
                    }
                }
            } else if (firstAnswer.equalsIgnoreCase("no")) {
                System.out.println("Hope you enjoyed it, we look forward to seeing you again=)");
                break;
            }

            System.out.println("May be you want to hear some interesting facts about movies?");
            System.out.println("Please provide the name of a movie or actor you'd like to hear interesting facts about: ");

            String text = scanner.nextLine();

            if(Objects.nonNull(text)) {
                getMovieFact(text);
            }
        }
    }

    private void getMoviePrompt(String movieName) throws ExecutionException, InterruptedException {
        movieApiService.serveMovie(movieName)
                .doOnNext(movie -> {
                    if(movie.getResponse()) {
                        System.out.println("Title: " + movie.getTitle());
                        System.out.println("Genre: " + movie.getGenre());
                        System.out.println("Release year: " + movie.getYear());
                        System.out.println("writer: " + movie.getWriter());
                        System.out.println("Actors: " + movie.getActors());
                        System.out.println("Time: " + movie.getRuntime());
                        System.out.println("Plot: " + movie.getPlot());
                    } else {
                        System.out.println("I'm sorry, I couldn't find that movie =(");
                    }
                }).toFuture().get();
    }

    private void getMovieFact(String movieName) throws ExecutionException, InterruptedException {
        gptService.getChatResponse(movieName)
                .doOnNext(movieFact -> {
                    if(StringUtils.hasLength(movieFact)) {
                        System.out.println(movieFact);
                    } else {
                        System.out.println("I'm sorry, I couldn't find that movie =(");
                    }
                }).toFuture().get();
    }
}
