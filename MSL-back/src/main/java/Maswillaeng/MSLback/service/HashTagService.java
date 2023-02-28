package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.domain.repository.HashTagRepository;
import Maswillaeng.MSLback.domain.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HashTagService {
    private final TagRepository tagRepository;
    private final HashTagRepository hashTagRepository;

    public List<HashTag> insertHashTagList(List<String> hashTagList, Post post) {

        List<Tag> existHashTagList = tagRepository.findByNameList(hashTagList);

        return hashTagList.stream()
                .map(tagName -> existHashTagList.stream()
                        .filter(t -> t.getName().equals(tagName))
                        .findFirst()
                        .map(tag -> new HashTag(tag, post))
                        .orElseGet(() -> new HashTag(new Tag(tagName), post)))
                .toList();
    }



        public void deleteHashTagList(List<String> removeTags, Post post) {
            hashTagRepository.deleteByPostId(post.getId());
            tagRepository.deleteByIds(removeTags.stream().filter(t->hashTagRepository.findByNames(t).size() == 0).toList());
        }



    public List<HashTag> updateHashTagList(List<String> updateHashTagList, Post post) {
        List<HashTag> oldHashTagList = hashTagRepository.findByPost(post);
        List<String> oldStringTagList = oldHashTagList.stream().map(h -> h.getTag().getName()).collect(Collectors.toCollection(ArrayList::new));

        List<String> removeHashTag = oldStringTagList.stream()
                .filter(old -> updateHashTagList.stream().noneMatch(Predicate.isEqual(old)))
                .collect(Collectors.toList());

        List<String> insertHashTag = updateHashTagList.stream()
                .filter(update -> oldStringTagList.stream().noneMatch(Predicate.isEqual(update)))
                .collect(Collectors.toList());

        deleteHashTagList(removeHashTag,post);


        List<HashTag>  resultHashTagList =  insertHashTagList(insertHashTag ,post);
        return resultHashTagList;
    }


}
