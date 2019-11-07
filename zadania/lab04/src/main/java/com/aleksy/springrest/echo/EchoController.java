package com.aleksy.springrest.echo;

import com.aleksy.springrest.echo.data.WordWrapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/echo")
public class EchoController {
    
    
    // 1.1 @GetMapping("{word}")
//    public String echo(@PathVariable("word") String word) {
//        return word;
//    }

    // 1.2
//    @GetMapping
//    public String echo(@RequestParam("word") String word) {
//        return word;
//    }


    // 1.3
//    @PostMapping
//    public WordWrapper echoPost(@RequestBody WordWrapper wordWrapper) {
//        return wordWrapper;
//    }

    //1.4
    @PostMapping
    public String echoDeserialize(@RequestBody WordWrapper wordWrapper) {
        return wordWrapper.getWord();
    }

    // 1.5
    @GetMapping("{word}")
    public WordWrapper getJson(@PathVariable("word") String word) {
        return new WordWrapper(word);
    }
}
