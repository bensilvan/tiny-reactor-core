# Tiny Reactor Core ☢️

> A minimalistic re-implementation of Project Reactor's `reactor-core` library for educational purposes.

<img src="https://avatars.githubusercontent.com/u/4201559?s=280&v=4" alt="Tiny Reactor Logo" width="20"/>

---

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)

---

## Introduction 

Tiny Reactor Core is a simplified version of the great Project Reactor's `reactor-core` library that implemented the `reactive-streams` specification. The goal of this project is to provide a lightweight alternative for those who want to understand the core principles behind reactive programming without diving deep into a fully-fledged library. This can be especially useful for students, educators, and anyone curious about the internals of reactive systems.

---

## Features 💥

- **Simplified Core**: Only the fundamental reactive types (`Mono` as `MyMono`, `Flux` as `MyFlux`) and basic operators are included.
- **Lightweight**: Stripped-down to the most essential features for educational clarity.
- **Open-Source**: Modify, distribute, and collaborate!

---

## Installation

To get started with Tiny Reactor Core:

```bash
# Clone this repository
git clone https://github.com/bensilvan/reactive-streams-example.git

# Build the project
./gradlew build
```

---

## Usage

Here's a basic example to get you started:

```java
import org.tinyReactorCore.example.Impl.constans.Schedulers;
import org.tinyReactorCore.example.Impl.publishers.MyFlux;
import org.tinyReactorCore.example.Impl.publishers.MyMono;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        MyFlux.just(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
                .publishOn(Schedulers.getWorkerPool())
                .map(msg -> {
                    System.out.println("running some heavy cpu operation on: " + msg + " on thread" + Thread.currentThread());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        System.out.println("Exception on thread.sleep " + e.getMessage());
                    }
                    return msg;
                })
                .flatMap(msg -> {
                    System.out.println("start async call after the heavy cpu operation: " + msg + " on thread " + Thread.currentThread());
                    return MyMono.delay(msg, Duration.ofSeconds(msg * 2)); // simulate async call for db \ api
                }, 3) // 3 async calls can run in the same time concurrently
                .subscribeOn(Executors.newSingleThreadExecutor())
                .subscribe(msg -> System.out.println("finish processing " + msg + " on thread " + Thread.currentThread()));
    }
}
```
---


## Contribution

Want to contribute? Awesome! Here are the steps:

1. Fork the repo.
2. Create a new branch (`git checkout -b feature/YourFeatureName`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add some awesome feature'`).
5. Push to the branch (`git push origin feature/YourFeatureName`).
6. Create a new Pull Request.
