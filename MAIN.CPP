#include <iostream>
#include <vector>
#include <string>
#include <regex>
#include <algorithm>
#include <curl/curl.h>

using namespace std;

// Callback function used by libcurl
size_t WriteCallback(void* contents, size_t size, size_t nmemb, string* output) {
    output->append((char*)contents, size * nmemb);
    return size * nmemb;
}

// Download HTML from URL
string downloadPage(const string& url) {
    CURL* curl = curl_easy_init();
    string html;

    if (curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url.c_str());
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, &html);
        curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, 1L);

        curl_easy_perform(curl);
        curl_easy_cleanup(curl);
    }

    return html;
}

// Main function
void printSecretMessage(const string& url) {
    url

    string html = downloadPage(url);

    // Extract table cells
    regex tdRegex("<td[^>]*>(.*?)</td>");
    sregex_iterator it(html.begin(), html.end(), tdRegex);
    sregex_iterator end;

    vector<string> cells;

    while (it != end) {
        string value = (*it)[1].str();

        // Remove HTML tags if present
        value = regex_replace(value, regex("<.*?>"), "");

        cells.push_back(value);
        ++it;
    }

    vector<tuple<int,int,string>> points;

    // Skip header row (3 cells)
    for (int i = 3; i + 2 < cells.size(); i += 3) {

        string first = cells[i];
        string second = cells[i + 1];
        string third = cells[i + 2];

        int x, y;
        string ch;

        try {
            x = stoi(first);
            ch = second;
            y = stoi(third);
        }
        catch (...) {
            ch = first;
            x = stoi(second);
            y = stoi(third);
        }

        points.push_back({x, y, ch});
    }

    if (points.empty()) {
        cout << "No data found\n";
        return;
    }

    int maxX = 0, maxY = 0;

    for (auto &[x, y, ch] : points) {
        maxX = max(maxX, x);
        maxY = max(maxY, y);
    }

    vector<vector<string>> grid(maxY + 1,
                                vector<string>(maxX + 1, " "));

    for (auto &[x, y, ch] : points)
        grid[y][x] = ch;

    for (auto &row : grid) {
        for (auto &c : row)
            cout << c;
        cout << '\n';
    }
}