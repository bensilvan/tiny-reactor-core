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
import org.tinyReactorCore.example.Impl.publishers.MyFlux;
import org.tinyReactorCore.example.Impl.StringsSubscriber;

import java.util.List;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        var subscriber = new StringsSubscriber();

        MyFlux.just(List.of(1,2,3))
                .publishOn(Executors.newSingleThreadExecutor())
                .map(x -> {
                    System.out.println("inside map: running on : " + Thread.currentThread());
                    return x.toString();
                })
                .subscribe();
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
