# splip - a calculator for splitting & tipping

## jessica chen

**splip™️** computes the tip and total amount for a bill, split across the total number of people. The app uses the base amount and tip percentage to calculate the amount owed, and it also describes the quality of service based on the tip.

Time spent: **6** hours spent in total

## Functionality 

The following **required** functionality is completed:

* [x] User can enter in a bill amount (total amount to tip on)
* [x] User can enter a tip percentage (what % the user wants to tip).
* [x] The tip and total amount are updated immediately when any of the inputs changes.
* [x] The user sees a label or color update based on the tip amount. 

The following **extensions** are implemented:

* [x] Custom colors palette selected
* [x] Changed the text color & font
* [x] Added emojis to the text describing the tip (“poor”, “good”, etc)
* [x] Added the ability to split the bill across any number of people
* [x] Show currency symbols

## Video Walkthrough

Here's a walkthrough of implemented calculator:

<img src='/Assignment1/walkthrough.gif?raw=true' title='Video Walkthrough' width='40%' alt='Video Walkthrough' />

## Notes

When adding the option to input the # of people, I ran into the same issue with the base amount where the app would crash if the input was blank. To address this bug, I added a check to default to 1 person if any invalid inputs (0 or blank) were given. Otherwise, the input would be converted and used to divide the total base + tip.

## License

    Copyright [2020] [jessica chen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
