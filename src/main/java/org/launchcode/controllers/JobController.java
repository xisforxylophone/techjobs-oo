package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.JobField;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        Job someJob = jobData.findById(id);
        model.addAttribute("someJob", someJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute(jobForm);
            return "new-job";

        } else {
            Job newJob = new Job(jobForm.getName(), jobData.getEmployers().findById(jobForm.getEmployerId()),
                    jobData.getLocations().findById(jobForm.getLocationId()),
                    jobData.getPositionTypes().findById(jobForm.getPositionTypeId()),
                    jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));

            jobData.add(newJob);
            model.addAttribute("someJob", newJob);
            return "redirect:/job?id=" + newJob.getId();


        }
    }
}
