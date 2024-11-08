Task description
## Launch URL
[https://www.saucedemo.com/](https://www.saucedemo.com/)

## Test Cases

### UC-1: Test Login Form with Empty Credentials
1. Type any credentials into the "Username" and "Password" fields.
2. Clear the inputs.
3. Hit the "Login" button.
4. Check the error message: "Username is required".

### UC-2: Test Login Form with Username Only
1. Type any credentials in the "Username" field.
2. Enter a password.
3. Clear the "Password" input.
4. Hit the "Login" button.
5. Check the error message: "Password is required".

### UC-3: Test Login Form with Valid Credentials
1. Type credentials in the "Username" field that are listed under the "Accepted usernames" section.
2. Enter the password as "secret_sauce".
3. Click on the "Login" button.
4. Validate the title “Swag Labs” on the dashboard.

## Additional Requirements
- Provide parallel execution.
- Add logging for tests.
- Use Data Provider to parameterize tests.
- Ensure all tasks are supported by these three conditions: UC-1, UC-2, and UC-3.